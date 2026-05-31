package com.example.team_koeln_bonn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Report
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.team_koeln_bonn.ui.theme.*

@Composable
fun MenuScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 28.dp, vertical = 32.dp)
    ) {

        Icon(
            imageVector = Icons.Outlined.Menu,
            contentDescription = "Menü",
            tint = NavActive,
            modifier = Modifier.size(34.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MenuButton(
                text = "Karten",
                icon = Icons.Outlined.LocationOn,
                color = AppBlue
            )

            MenuButton(
                text = "SOS",
                icon = Icons.Outlined.Warning,
                color = AppLightRed
            )
        }

        Spacer(modifier = Modifier.height(30.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MenuButton(
                text = "Chat",
                icon = Icons.Outlined.Chat,
                color = AppOrange
            )

            MenuButton(
                text = "Melden",
                icon = Icons.Outlined.Report,
                color = AppPurple
            )
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    icon: ImageVector,
    color: Color
) {
    Column(
        modifier = Modifier
            .size(125.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(16.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = ButtonContent,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = text,
            color = ButtonContent,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    Team_Koeln_BonnTheme {
        MenuScreen()
    }
}