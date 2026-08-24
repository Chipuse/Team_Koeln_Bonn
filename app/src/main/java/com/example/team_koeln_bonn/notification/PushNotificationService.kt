package com.example.team_koeln_bonn.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM_TOKEN", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FCM_MESSAGE", "Nachricht erhalten!")

        val title = message.notification?.title
            ?: message.data["title"]
            ?: "Neue Barriere"

        val body = message.notification?.body
            ?: message.data["body"]
            ?: "Eine neue Barriere wurde gemeldet."

        BarrierNotificationManager(this)
            .showNotification(title, body)
    }
}