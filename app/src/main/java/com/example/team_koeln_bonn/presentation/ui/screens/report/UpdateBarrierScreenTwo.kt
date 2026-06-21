package com.example.team_koeln_bonn.presentation.ui.screens.report

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.team_koeln_bonn.domain.model.UpdateAffectedGroup
import com.example.team_koeln_bonn.presentation.ui.theme.MeldungHintergrund
import com.example.team_koeln_bonn.presentation.ui.theme.Team_Koeln_BonnTheme
import com.example.team_koeln_bonn.presentation.viewModel.BarrierUpdateViewModel

@Composable
fun UpdateBarrierScreenTwo(
    viewModel: BarrierUpdateViewModel = viewModel(),
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {

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


            Text(
                text = "",//weg
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(90.dp)) // GEÄNDERT: Weniger Abstand, damit Navigation unten sichtbar bleibt

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

                Spacer(modifier = Modifier.height(24.dp)) // Weniger Abstand, damit Navigation unten sichtbar bleibt

                // Inhalt vom Figma Screen
                Text(
                    text = "Wer ist betroffen?\nWähle mindestens eine Option.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp)) // Abstandssache hier auch geändert

                // Auswahl der betroffenen Personengruppen
                UpdateAffectedGroup.entries.forEach { group ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = group in viewModel.selectedGroups,
                            onCheckedChange = {
                                viewModel.toggleGroup(group)
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = group.label,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp)) // Weniger Abstand zwischen Checkboxen
                }

                Spacer(modifier = Modifier.height(12.dp)) //Weniger Abstand, damit Navigation unten sichtbar bleibt

                // Navigation zwischen den Schritten
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        modifier = Modifier.clickable {
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
                        modifier = Modifier.clickable {
                            onNextClick()
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "weiter",
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
}

@Preview(showBackground = true)
@Composable
fun UpdateBarrierScreenTwoPreview() {
    Team_Koeln_BonnTheme {
        UpdateBarrierScreenTwo(
            onBackClick = {},
            onNextClick = {}
        )
    }
}