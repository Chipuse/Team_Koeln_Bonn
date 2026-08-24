package com.example.team_koeln_bonn

import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.team_koeln_bonn.presentation.ui.OurApp
import com.example.team_koeln_bonn.presentation.ui.theme.Team_Koeln_BonnTheme
import org.osmdroid.config.Configuration
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.content.ContextCompat.getSystemService
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.team_koeln_bonn.notification.BarrierFirestoreListener



class MainActivity : ComponentActivity() {

    private lateinit var barrierFireStoreListener: BarrierFirestoreListener

    //für notification Permission
    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                println("Notifications erlaubt")
            } else {
                println("Notifications nicht erlaubt")
            }
        }
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //für notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }

        createNotificationChannel()
        barrierFireStoreListener = BarrierFirestoreListener(applicationContext)
        barrierFireStoreListener.startListening()

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("FCM_TOKEN", "Token konnte nicht geladen werden", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result
                Log.d("FCM_TOKEN", token)
            }

        // Alle Geräte abonnieren das Barrier-Topic
        FirebaseMessaging.getInstance()
            .subscribeToTopic("barriers")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM_TOPIC", "barriers erfolgreich abonniert")
                } else {
                    Log.e(
                        "FCM_TOPIC",
                        "Topic barriers konnte nicht abonniert werden",
                        task.exception
                    )
                }
            }

        enableEdgeToEdge()
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        Configuration.getInstance().userAgentValue =  packageName + " (+contact: mvollmer.dev@gmail.com)" //added contactinformation to user agent since some mapprovider require that

        setContent {
            Team_Koeln_BonnTheme {
                OurApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (::barrierFireStoreListener.isInitialized) {
            barrierFireStoreListener.stopListening()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                "barrier_notifications",
                "Barrier Updates",
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.description = "Benachrichtigungen über neue Barrieren"

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello Ceyda & Eylem & Mai! Welcome to $name",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Team_Koeln_BonnTheme {
        Greeting("Android")
    }
}
