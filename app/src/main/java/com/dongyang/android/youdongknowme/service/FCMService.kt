package com.dongyang.android.youdongknowme.service

import com.dongyang.android.youdongknowme.standard.util.logd
import com.dongyang.android.youdongknowme.standard.util.logw
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        logw("New Token :: $token")
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        logd("Notification Title : {${message.notification?.title}")
        logd("Notification Body : {${message.notification?.body}")
        logd("Notification Data : {${message.data}")
    }
}