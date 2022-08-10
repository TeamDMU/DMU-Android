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
import com.dongyang.android.youdongknowme.data.local.dao.AlarmDao
import com.dongyang.android.youdongknowme.data.local.entity.AlarmEntity
import com.dongyang.android.youdongknowme.standard.MyApplication
import com.dongyang.android.youdongknowme.standard.util.mapDepartmentCodeToKorean
import com.dongyang.android.youdongknowme.standard.util.mapKeywordEnglishToKorean
import com.dongyang.android.youdongknowme.ui.view.alarm.AlarmActivity
import com.dongyang.android.youdongknowme.ui.view.splash.SplashActivity
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

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data

        if (data.isNotEmpty()) {
            val departmentCode: String = data["major_code"] ?: ""
            val noticeNum: Int = data["num"]?.toInt() ?: 0
            val title: String = data["title"] ?: ""
            val keyword: String = data["keyword"] ?: ""

            val department = mapDepartmentCodeToKorean(departmentCode.toInt())
            val koreanKeyword = mapKeywordEnglishToKorean(keyword)

            val alarmData =
                AlarmEntity(null, title, department, koreanKeyword, noticeNum, false)

            if (department == resources.getString(R.string.school)) {
                if (SharedPreference.getIsAccessSchoolAlarm()) {
                    createNotificationChannel(koreanKeyword, department)
                    insertAlarmData(alarmData)
                }
            } else {
                if (SharedPreference.getIsAccessDepartAlarm() && isUserKeyword(department)) {
                    createNotificationChannel(koreanKeyword, department)
                    insertAlarmData(alarmData)
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

        val splashIntent = Intent(this, SplashActivity::class.java)
        val alarmIntent = Intent(this, AlarmActivity::class.java)

        val pendingIntent = if(MyApplication.isForeground) {
            PendingIntent.getActivity(
                this, 0, alarmIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(
                this, 0, splashIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

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

    // 알람이 오면 알림함에 데이터를 저장
    private fun insertAlarmData(alarmEntity: AlarmEntity) {
        val alarmDao: AlarmDao by inject()

        // 학교 -> 동양미래대학교 변환
        if (alarmEntity.department == resources.getString(R.string.school)) {
            alarmEntity.department = resources.getString(R.string.dmu)
        }

        scope.launch {
            alarmDao.insertAlarm(alarmEntity)
        }
    }

    private fun isUserKeyword(department: String): Boolean {
        return department == SharedPreference.getDepartment()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}