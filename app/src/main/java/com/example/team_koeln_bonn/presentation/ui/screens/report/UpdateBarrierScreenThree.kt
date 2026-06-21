package com.example.team_koeln_bonn.presentation.ui.screens.report

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.team_koeln_bonn.presentation.ui.theme.MeldungHintergrund
import com.example.team_koeln_bonn.presentation.ui.theme.Team_Koeln_BonnTheme
import com.example.team_koeln_bonn.presentation.viewModel.BarrierUpdateViewModel

@Composable
fun UpdateBarrierScreenThree(
    viewModel: BarrierUpdateViewModel = viewModel(),
    onBackClick: () -> Unit, // Klick zwischen den Screens
    onSubmitClick: () -> Unit // Meldung abschicken
) {

    var showConfirmDialog by remember { mutableStateOf(false) } // Sicherheitsabfrage anzeigen

    //Grundgerüst
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp, vertical = 56.dp)
    ) {

        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {


            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = "", //weg
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(110.dp))

        // Meldung Card
        Card(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MeldungHintergrund
            )
        ) {

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
                        text = "Barriere updaten",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Benachrichtigungen",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Inhalt vom Figma Screen
                Text(
                    text = "Beschreibe das Problem.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Beschreibungstext für Problem eingeben
                OutlinedTextField(
                    value = viewModel.description,
                    onValueChange = {
                        viewModel.updateDescription(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    placeholder = {
                        Text(text = "Beschreibung eingeben...")
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Navigation zwischen den Schritten
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        modifier = Modifier.clickable { //zurück klickbar
                            onBackClick()
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Zurück",
                            tint = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "zurück",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Row(
                        modifier = Modifier.clickable { //sicherheitsabfrage vor dem Abschicken öffnen
                            showConfirmDialog = true
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Meldung\nabschicken",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Weiter",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }

    if (showConfirmDialog) { // sicherheitsabfrage vor dem Abschicken
        AlertDialog(
            onDismissRequest = {
                showConfirmDialog = false
            },
            title = {
                Text(text = "Sind Sie sicher?")
            },
            text = {
                Text(text = "Möchten Sie diese Meldung wirklich abschicken?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmDialog = false
                        onSubmitClick()
                    }
                ) {
                    Text(text = "Ja")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showConfirmDialog = false
                    }
                ) {
                    Text(text = "Nein")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UpdateBarrierScreenThreePreview() {
    Team_Koeln_BonnTheme {
        UpdateBarrierScreenThree(
            onBackClick = {},
            onSubmitClick = {}
        )
    }
}