package com.example.team_koeln_bonn.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost

enum class AppScreen(){
    Menu,
    Map,
    Community,
    Report
}

/*
@Composable
fun OurNavHost(){
    NavHost(
        navController = NavController,
        startDestination = AppScreen.Menu.toString(),
        modifier = Modifier
    ){

    }
}
*/
