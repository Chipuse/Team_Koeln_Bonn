package com.example.team_koeln_bonn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
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
fun CommunityScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 28.dp, vertical = 32.dp)
    ) {

        Spacer(modifier = Modifier.height(36.dp))

        CommunityBar("Gruppen-Chat", Icons.Outlined.Groups, AppBlue)
        Spacer(modifier = Modifier.height(18.dp))

        CommunityBar("Stadt-Chat", Icons.Outlined.Apartment, AppLightRed)
        Spacer(modifier = Modifier.height(18.dp))

        CommunityBar("Uni-Chat", Icons.Outlined.School, AppOrange)
        Spacer(modifier = Modifier.height(18.dp))

        CommunityBar("Nachbarschaft", Icons.Outlined.Home, AppPurple)
    }
}

@Composable
fun CommunityBar(
    text: String,
    icon: ImageVector,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .background(color, RoundedCornerShape(16.dp))
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = ButtonContent,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            color = ButtonContent,
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Benachrichtigung",
            tint = ButtonContent,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    Team_Koeln_BonnTheme {
        CommunityScreen()
    }
}