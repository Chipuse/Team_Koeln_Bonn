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

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("FCM_TOKEN", "Token konnte nicht geladen werden")
                    return@addOnCompleteListener
                }

                val token = task.result
                Log.d("FCM_TOKEN", token)
            }

        enableEdgeToEdge()
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        setContent {
            Team_Koeln_BonnTheme {
                OurApp()
            }
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
