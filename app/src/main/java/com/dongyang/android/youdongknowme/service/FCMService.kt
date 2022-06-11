package com.dongyang.android.youdongknowme.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.standard.util.logw
import com.dongyang.android.youdongknowme.standard.util.mapDepartmentCodeToKorean
import com.dongyang.android.youdongknowme.standard.util.mapKeywordEnglishToKorean
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FCMService : FirebaseMessagingService() {

    companion object {
        const val channelId = "Youdongknowme_id"
        const val channelName = "Youdongknowme_name"
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

    private fun createNotificationChannel(keyword: String, department: String) {
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

        // Foreground 에 있을 때 알림 처리
        val splashIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent =
            PendingIntent.getActivity(
                this, 0, splashIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

        val defaultSoundUri: Uri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setColor(ContextCompat.getColor(this, R.color.main))
            .setContentTitle("${keyword}(이)가 포함된 공지사항이 ${department}에 올라왔어요!")
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            val notificationId = SystemClock.uptimeMillis().toInt()
            notify(notificationId, notificationBuilder.build())
        }
    }
}