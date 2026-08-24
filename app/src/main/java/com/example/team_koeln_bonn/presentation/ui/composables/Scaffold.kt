package com.example.team_koeln_bonn.presentation.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.team_koeln_bonn.R
import kotlin.collections.forEach

@Composable
fun OurTopBar(modifier : Modifier = Modifier){
    Box(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp, 15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                painter = painterResource(R.drawable.moco_app_icon_not_filled),
                contentDescription = "Our App Icon",
                tint = Color.White
            )
            Text(
                "Barrier Spotter - by TeamKölnBonn",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
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
