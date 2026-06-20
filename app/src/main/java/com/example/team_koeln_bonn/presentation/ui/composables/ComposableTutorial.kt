package com.example.team_koeln_bonn.presentation.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.team_koeln_bonn.Greeting
import com.example.team_koeln_bonn.presentation.ui.theme.Team_Koeln_BonnTheme


@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun GreetingPreview() {
    Team_Koeln_BonnTheme {
        Greeting("Android")
    }
}


@Composable
fun TutorialMessageCard(msg : TutorialMessage){
    Team_Koeln_BonnTheme {
        Column {
            Text(
                "Message Display:",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(60.dp))
            Row {
                Text(
                    msg.author,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(50.dp))
                Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 5.dp) {
                    Text(
                        msg.body,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Row {
            }
        }
    }
}
data class TutorialMessage(val author: String, val body: String)
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun TutorialPreviewMessageCard(){
    Team_Koeln_BonnTheme() {
        Surface() {
            TutorialMessageCard(
                TutorialMessage(
                    "Authot",
                    "Message"
                )
            )
        }
    }
}
