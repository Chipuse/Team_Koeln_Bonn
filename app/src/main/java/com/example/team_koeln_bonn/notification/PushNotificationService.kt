package com.example.team_koeln_bonn.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.util.Log

class PushNotificationService: FirebaseMessagingService() {

    //authenticate to fight against attacks
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("FCM_TOKEN", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FCM_MESSAGE", "Nachricht erhalten!")

    }

}