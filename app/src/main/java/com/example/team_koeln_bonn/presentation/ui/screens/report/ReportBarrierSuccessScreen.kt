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
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.navigation.compose.rememberNavController
import com.example.team_koeln_bonn.presentation.ui.screens.AppScreen

@Composable
fun ReportBarrierSuccessScreen(
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
        /*Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Zurück Pfeil
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(28.dp)
                    .clickable{
                        //Später navigation
                        navController.navigate(AppScreen.ReportBarrierScreen.name)
                    }
            )

            // Abstand zwischen Pfeil und Titel
            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = "Meldung",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }*/
        // Abstand Header und Karte
        Spacer(modifier = Modifier.height(20.dp))

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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
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
                    text = "Danke!\n\nDeine Meldung wird zunächst geprüft!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.clickable{
                    //Zurück zur Startseite. Nur navigation
                    navController.navigate(AppScreen.Menu.name)
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "zurück"
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "zurück zur Startseite",
                    fontSize = 18.sp
                )
            }
        }
    }
    }
}




@Preview (showBackground = true)
@Composable
fun PreviewReportBarrierSuccessScreen(){
    val navController = rememberNavController()

    Team_Koeln_BonnTheme {
        ReportBarrierSuccessScreen(navController)
    }
}
