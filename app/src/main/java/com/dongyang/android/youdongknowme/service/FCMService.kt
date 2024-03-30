package com.dongyang.android.youdongknowme.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title ?: "기본 제목"
        val message = remoteMessage.notification?.body ?: "기본 메시지"

        // 알림 채널 ID
        val channelId = "your_channel_id"

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

        // Intent 및 PendingIntent 생성
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // 알림 생성
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(com.dongyang.android.youdongknowme.R.mipmap.ic_logo) // 알림 아이콘 설정
            .setContentTitle(title) // 알림 제목
            .setContentText(message) // 알림 내용
            .setAutoCancel(true) // 터치 시 자동으로 삭제되도록 설정
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)) // 알림 소리 설정
            .setVibrate(longArrayOf(0, 1000, 500, 1000)) // 진동 패턴 설정
            .setContentIntent(pendingIntent) // 알림을 터치했을 때 실행할 Intent 설정

        // 알림 표시
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        SharedPreference.setFcmToken(token)
    }
}