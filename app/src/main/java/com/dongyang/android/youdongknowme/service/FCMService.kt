package com.dongyang.android.youdongknowme.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.dongyang.android.youdongknowme.ui.view.detail.DetailActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.notification?.title ?: "기본 제목"
        val messageBody = message.notification?.body ?: "기본 메시지"
        val url = message.data["url"] ?: ""

        // 알림 채널 ID
        val channelId = "DMforU"

        // NotificationManager 인스턴스 생성
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Android O 이상에서는 알림 채널이 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Channel Name"
            val channelDescription = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = channelDescription
            notificationManager.createNotificationChannel(channel)
        }

        // MainActivity를 시작하는 Intent 생성
        val mainIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        // DetailActivity를 열기 위한 Intent 생성
        val detailIntent = if (!url.isNullOrEmpty()) {
            DetailActivity.newIntent(this, url)
        } else {
            null // URL이 없는 경우에는 null 할당
        }

        // PendingIntent 생성
        val pendingIntent = if (detailIntent != null) {
            TaskStackBuilder.create(this).run {
                addNextIntent(mainIntent)
                addNextIntentWithParentStack(detailIntent) // DetailActivity를 부모 스택으로 추가
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        } else {
            PendingIntent.getActivity(
                this,
                0,
                mainIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        // 알림 생성
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(com.dongyang.android.youdongknowme.R.mipmap.ic_logo) // 알림 아이콘 설정
            .setContentTitle(title) // 알림 제목
            .setContentText(messageBody) // 알림 내용
            .setAutoCancel(true) // 터치 시 자동으로 삭제되도록 설정
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)) // 알림 소리 설정
            .setVibrate(longArrayOf(0, 1000, 500, 1000)) // 진동 패턴 설정
            .setContentIntent(pendingIntent) // 알림을 터치했을 때 실행할 Intent 설정

        // 알림 표시
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }
}