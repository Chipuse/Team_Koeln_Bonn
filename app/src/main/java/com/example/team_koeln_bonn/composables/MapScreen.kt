package com.example.team_koeln_bonn.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun DisplayMapScreen(){
    MapScreen()
}

//https://developer.android.com/develop/sensors-and-location/location/maps-and-places

@Composable
fun MapScreen(modifier : Modifier = Modifier){
    Scaffold(
        modifier = Modifier,
        //top app bar content
        topBar = {
            OurTopBar()
        },
        floatingActionButton = {

        },

        //here belong the contents of the main window in the scaffold
        content = {
                paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .consumeWindowInsets(paddingValues)
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
        },
        bottomBar = {
            OurBottomBar()
        }
    )
}