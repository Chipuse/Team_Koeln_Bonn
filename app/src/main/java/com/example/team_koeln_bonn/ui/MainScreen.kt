package com.example.team_koeln_bonn.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.team_koeln_bonn.ui.composables.BottomBarButton
import com.example.team_koeln_bonn.ui.composables.OurBottomBar
import com.example.team_koeln_bonn.ui.composables.OurTopBar
import com.example.team_koeln_bonn.ui.screens.AppScreen
import com.example.team_koeln_bonn.ui.screens.map.MapScreen
import com.example.team_koeln_bonn.ui.screens.menu.MenuScreen
import com.example.team_koeln_bonn.ui.screens.report.ReportBarrierScreen


//entrypoint for our appscreens
@Composable
fun OurApp(
    //viewmodel,
    navController : NavHostController = rememberNavController()
){
    Scaffold(
        modifier = Modifier,
        //top app bar content
        topBar = {
            OurTopBar()
        },
        floatingActionButton = {

        },
        content = {
                paddingValues ->
                // ToDo val uiState by viewModel.uiState.collectAsState()
            NavHost(
                navController = navController,
                startDestination = AppScreen.Menu.name,
                modifier = Modifier.padding(paddingValues)

            )
            //here the composables all can be put into the scaffold main body via navigation
            {
                composable(route = AppScreen.Menu.name){
                    MenuScreen(
                        //modifier = Modifier.fillMaxSize()
                    )
                }

                composable(route = AppScreen.Report.name){
                    ReportBarrierScreen(
                        //modifier = Modifier.fillMaxSize()
                    )
                }

                composable(route = AppScreen.Map.name){
                    MapScreen(
                        //information to display mapscreen correctly
                        modifier = Modifier.fillMaxSize()
                    )
                }
                /*
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .consumeWindowInsets(paddingValues)
                ) {
                    MapScreen()
                }
                */
            }

        },
        bottomBar = {
            OurBottomBar(buttons = listOf(
                BottomBarButton(icon = Icons.Filled.Apps, contentDescription = "Menu Button", onClickBehavior = { navController.popBackStack(); navController.navigate(AppScreen.Menu.name)  }), //ToDo find better solution than calling popbackstack before navigating
                BottomBarButton(icon = Icons.Filled.Map, contentDescription = "Map Button", onClickBehavior = {navController.popBackStack(); navController.navigate(AppScreen.Menu.name); navController.navigate(AppScreen.Map.name) }),
                BottomBarButton(icon = Icons.Filled.AddLocationAlt, contentDescription = "Report Button", onClickBehavior = {navController.popBackStack(); navController.navigate(AppScreen.Menu.name); navController.navigate(AppScreen.Report.name) })
            )
            )
        }
    )
}