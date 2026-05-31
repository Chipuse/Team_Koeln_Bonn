package com.example.team_koeln_bonn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.team_koeln_bonn.ui.theme.*

@Composable
fun SosScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 28.dp, vertical = 32.dp)
    ) {

        Spacer(modifier = Modifier.height(42.dp))

        // SOS Bars
        SosBar(
            title = "NOTDIENST",
            number = "112",
            color = AppPurple
        )

        Spacer(modifier = Modifier.height(22.dp))

        SosBar(
            title = "POLIZEI",
            number = "110",
            color = AppBlue
        )

        Spacer(modifier = Modifier.height(22.dp))

        SosBar(
            title = "COMMUNITY ANFRAGEN",
            number = "",
            color = AppOrange
        )
    }
}

@Composable
fun SosBar(
    title: String,
    number: String,
    color: Color
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 18.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {

            Text(
                text = title,
                color = ButtonContent,
                style = MaterialTheme.typography.labelLarge
            )

            if (number.isNotEmpty()) {

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = number,
                    color = ButtonContent,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Outlined.Call,
            contentDescription = "Anrufen",
            tint = ButtonContent,
            modifier = Modifier.size(34.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SosScreenPreview() {

    Team_Koeln_BonnTheme {

        SosScreen()

    }
}