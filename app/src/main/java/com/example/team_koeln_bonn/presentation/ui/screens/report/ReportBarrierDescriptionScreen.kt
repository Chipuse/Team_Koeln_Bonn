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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.navigation.compose.rememberNavController
import com.example.team_koeln_bonn.presentation.ui.screens.AppScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.team_koeln_bonn.presentation.viewModel.BarrierUpdateViewModel

@Composable
fun ReportBarrierDescriptionScreen(
    navController: NavController
) {

    val barrierUpdateViewModel: BarrierUpdateViewModel = viewModel()
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
                        .clickable {
                            //Zur vorherigen Seite, keine Datenübergabe
                            navController.popBackStack()
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
                    // Titel + Glockes
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
                        text = "Beschreibe das Problem",
                        fontSize = 20.sp,
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    //Freitextfelt. Später echte Funktionalität

                    OutlinedTextField(
                        value = barrierUpdateViewModel.description,
                        onValueChange = {
                            barrierUpdateViewModel.updateDescription(it)
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(115.dp),

                        placeholder = {
                            Text("Beschreibe hier das Problem...")
                        },

                        shape = RoundedCornerShape(14.dp),

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFD9D9D9),
                            unfocusedContainerColor = Color(0xFFD9D9D9),

                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    //GPS hinweis ncoh keine Funktion
                    Text(
                        text = "Erlaube den GPS Zugriff zur\nStandortermittlung.",
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    //GPS BUTTON

                    Button(
                        onClick = {
                            //Später GPS Zugriff anfordern. Aktuell nur Navigation zum nächsten Schritt
                            navController.navigate(AppScreen.ReportBarrierSuccessScreen.name)
                        },
                        modifier = Modifier.width(170.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD7E4FF),
                            contentColor = Color.Black
                        )
                    ) {

                        Text(
                            text = "Ort ermitteln",
                            fontSize = 18.sp
                        )
                    }

                    // Schiebt die Navigation nach unten
                    Spacer(modifier = Modifier.weight(1f))

                    //Untere Navigation. Erstes zurück
                    Row(
                        modifier = Modifier.clickable {
                            //Aktuell nur Navigation
                            navController.navigate(AppScreen.ReportBarrierScreen.name)
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
                }
            }
        }
    }






    @Preview(showBackground = true)
    @Composable
    fun PreviewReportBarrierDescriptionScreen() {
        val navController = rememberNavController()

        Team_Koeln_BonnTheme {
            ReportBarrierDescriptionScreen(navController)
        }
    }

