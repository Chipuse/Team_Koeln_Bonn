package com.example.team_koeln_bonn.presentation.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.collections.forEach

@Composable
fun OurTopBar(modifier : Modifier = Modifier){
    BottomAppBar(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.primary
    ){
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {}) {
                Icon(Icons.Filled.Menu, contentDescription = "Icon 1")
            }
            Text(
                "Title",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

data class BottomBarButton(val icon: ImageVector, val contentDescription : String = "", val onClickBehavior: () -> Unit = {})

@Composable
fun OurBottomBar(
    buttons : List<BottomBarButton>,
    modifier : Modifier = Modifier
){
    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary
    ){
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            buttons.forEach { button ->
                IconButton(
                    onClick = button.onClickBehavior) {
                    Icon(imageVector = button.icon, contentDescription = button.contentDescription)
                }
            }
        }
    }
}
