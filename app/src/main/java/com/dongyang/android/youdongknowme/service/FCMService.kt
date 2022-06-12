package com.dongyang.android.youdongknowme.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.data.local.UserDatabase
import com.dongyang.android.youdongknowme.data.local.dao.AlarmDao
import com.dongyang.android.youdongknowme.data.local.entity.AlarmEntity
import com.dongyang.android.youdongknowme.data.repository.FCMRepository
import com.dongyang.android.youdongknowme.standard.util.logw
import com.dongyang.android.youdongknowme.standard.util.mapDepartmentCodeToKorean
import com.dongyang.android.youdongknowme.standard.util.mapKeywordEnglishToKorean
import com.dongyang.android.youdongknowme.ui.view.keyword.KeywordActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class FCMService : FirebaseMessagingService() {

    companion object {
        const val channelId = "Youdongknowme_id"
        const val channelName = "Youdongknowme_name"
    }

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate() {
        super.onCreate()
        logw("Service onCreate")
    }

    override fun onNewToken(token: String) {
        logw("New Token :: $token")
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data

        if (data.isNotEmpty()) {
            val major_code: String? = data["major_code"]
            val notice_num: String? = data["num"]
            val title: String? = data["title"]
            val keyword: String? = data["keyword"]

            val department = mapDepartmentCodeToKorean(major_code!!.toInt())
            val koreanKeyword = mapKeywordEnglishToKorean(keyword!!)

            val alarmEntity = AlarmEntity(null, title!!, department, koreanKeyword, notice_num!!.toInt(), false)
            insertAlarmData(alarmEntity)

            if (department == "학교") {
                if (SharedPreference.getIsAccessSchoolAlarm()!!) {
                    createNotificationChannel(koreanKeyword, department)
                }
            } else {
                if (SharedPreference.getIsAccessDepartAlarm()!!) {
                    createNotificationChannel(koreanKeyword, department)
                }
            }
        }
    }

    private fun createNotificationChannel(
        keyword: String,
        department: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val description = "FCM 메세지 알림"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                channelId, channelName, importance
            ).apply {
                this.description = description
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

        val splashIntent = Intent(this, KeywordActivity::class.java).apply {
            // flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, splashIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val defaultSoundUri: Uri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setColor(ContextCompat.getColor(this, R.color.main))
            .setContentTitle("키워드 알림이 도착했어요!")
            .setContentText("${keyword}(이)가 포함된 공지사항이 ${department}에 올라왔어요!")
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            val notificationId = 100
            notify(notificationId, notificationBuilder.build())
        }
    }

    private fun insertAlarmData(alarmEntity: AlarmEntity) {
        val alarmDao : AlarmDao by inject()

        if(alarmEntity.department == "학교") {
            alarmEntity.department = "동양미래대학교"
        }

        scope.launch {
            alarmDao.insertAlarm(alarmEntity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}