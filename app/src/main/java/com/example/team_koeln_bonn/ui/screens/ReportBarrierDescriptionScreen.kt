package com.example.team_koeln_bonn.ui.screens

import com.example.team_koeln_bonn.ui.theme.Team_Koeln_BonnTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ReportBarrierDescriptionScreen() {
    // Gesamter Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp, vertical = 56.dp)
    ) {
        //Header Pfeil + Titel
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Zurück Pfeil
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(28.dp)
            )

            // Abstand zwischen Pfeil und Titel
            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = "Meldung",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
        // Abstand Header und Karte
        Spacer(modifier = Modifier.height(110.dp))

        //Meldung Card
        Card(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(350.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEFEFEF)
            )
        ) {
            //INhalt der Card
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Titel + Glocke
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Neue Meldung",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Glocke",
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Beschreibe die Barriere.",
                    fontSize = 20.sp
                )
            }
        }
    }
}

//Es kommen noch Checkboxen, Buttons, Navigation etc. später.


@Preview (showBackground = true)
@Composable
fun PreviewReportBarrierDescriptionScreen(){
    Team_Koeln_BonnTheme {
        ReportBarrierDescriptionScreen()
    }
}
