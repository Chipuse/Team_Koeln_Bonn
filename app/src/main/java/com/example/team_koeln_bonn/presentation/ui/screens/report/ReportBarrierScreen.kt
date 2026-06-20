package com.example.team_koeln_bonn.presentation.ui.screens.report

import com.example.team_koeln_bonn.presentation.ui.theme.Team_Koeln_BonnTheme
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.navigation.compose.rememberNavController

@Composable
fun ReportBarrierScreen(
    navController: NavController
) {
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
                .width(350.dp)
                .height(470.dp),
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
                    text = "Wer ist betroffen?\nWähle mindestens eine Option.",
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                //Optionen
                ReportOption(
                    "Gehbeeinträchtigte"
                )
                ReportOption(
                    "Sehbeeinträchtigte"
                )
                ReportOption(
                    "Hörbeeinträchtigte"
                )
                ReportOption(
                    "Sonstiges"
                )

                Spacer(modifier = Modifier.height(20.dp))
                Spacer(modifier = Modifier.weight(1f))

                //Navigation unten weiter und zurück
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    //Links pfeil + zurück
                    Row(
                        modifier = Modifier.clickable{
                            //nur Navigation
                            navController.popBackStack()
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "zurück"
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "zurück",
                            fontSize = 18.sp
                        )
                    }

                    //Rechts weiter + pfeil
                    Row(
                        modifier = Modifier.clickable{
                            //Später Datenübergabe hier nur Navigation
                            navController.navigate("reportDescription")
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "weiter",
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "weiter"
                        )

                        Spacer(modifier = Modifier.width(6.dp))
                    }

                }
            }
        }
    }
}

@Composable
fun ReportOption(text: String) {
    // Eine graue Zeile mit Checkbox + Text
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
            .background(Color(0xFFD9D9D9))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Noch nicht funktional, nur für UI
        Checkbox(
            checked = true,
            onCheckedChange = null,
            enabled = false
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}


@Preview (showBackground = true)
@Composable
fun PreviewReportBarrierScreen(){
    val navController = rememberNavController()
    Team_Koeln_BonnTheme {
        ReportBarrierScreen(navController)
    }
}
                                   