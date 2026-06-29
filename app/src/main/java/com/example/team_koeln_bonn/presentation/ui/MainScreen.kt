package com.example.team_koeln_bonn.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.EditLocationAlt
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.team_koeln_bonn.presentation.ui.composables.BottomBarButton
import com.example.team_koeln_bonn.presentation.ui.composables.OurBottomBar
import com.example.team_koeln_bonn.presentation.ui.composables.OurTopBar
import com.example.team_koeln_bonn.presentation.ui.screens.AppScreen
import com.example.team_koeln_bonn.presentation.ui.screens.map.MapScreen
import com.example.team_koeln_bonn.presentation.ui.screens.menu.MenuScreen
import com.example.team_koeln_bonn.presentation.ui.screens.report.ReportBarrierScreen
import com.example.team_koeln_bonn.presentation.ui.screens.report.ReportBarrierDescriptionScreen
import com.example.team_koeln_bonn.presentation.ui.screens.report.ReportBarrierSuccessScreen
import com.example.team_koeln_bonn.presentation.viewModel.BarrierUpdateViewModel
import com.example.team_koeln_bonn.presentation.ui.screens.report.UpdateBarrierScreenTwo
import com.example.team_koeln_bonn.presentation.ui.screens.report.UpdateBarrierScreenThree
import com.example.team_koeln_bonn.presentation.ui.screens.report.UpdateBarrierScreenFour
import com.example.team_koeln_bonn.presentation.viewModel.BarrierReportViewModel

//entrypoint for our appscreens
@Composable
fun OurApp(
    //viewmodel,
    navController : NavHostController = rememberNavController()
){

    val barrierReportViewModel: BarrierReportViewModel = viewModel() //gemeinsames Viewmodel für ReportBarrierDescription und dings ReportBarrierScreen unterschiedliche Funktionen jeder hat eigene ViewModel
    val barrierUpdateViewModel : BarrierUpdateViewModel = viewModel() //gemeinsames ViewModel UpdateBarrierScreenTwo und UpdateBarrierThree



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

                //hier geändert die ViewModel getrennt passend zu ReportBarrier
                composable(route = AppScreen.Report.name){
                    ReportBarrierScreen(
                        //modifier = Modifier.fillMaxSize()
                        navController = navController,
                        barrierReportViewModel = barrierReportViewModel //hier geändert
                    )
                }

                composable(route = AppScreen.Map.name){
                    MapScreen(
                        //information to display mapscreen correctly
                        modifier = Modifier.fillMaxSize(),
                        navController
                    )
                }

                composable(route = AppScreen.ReportBarrierDescriptionScreen.name) {
                    ReportBarrierDescriptionScreen(
                        navController = navController,
                        barrierReportViewModel = barrierReportViewModel //hier auch
                    )
                }

                composable(route = AppScreen.ReportBarrierSuccessScreen.name) {
                    ReportBarrierSuccessScreen(navController)
                }

                composable(route = AppScreen.ReportBarrierScreen.name) {
                    ReportBarrierScreen(
                        navController = navController,
                        barrierReportViewModel = barrierReportViewModel // auch
                    )
                }

                //Navigation zu UpdateBarrierScreens
                composable(route = AppScreen.UpdateBarrierScreenTwo.name) {
                    UpdateBarrierScreenTwo(
                        viewModel = barrierUpdateViewModel,//gemeinsames Viewmodel übergeben die Zwischenspeicherung
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onNextClick = {
                            navController.navigate(AppScreen.UpdateBarrierScreenThree.name)
                        }
                    )
                }

                composable(route = AppScreen.UpdateBarrierScreenThree.name) {
                    UpdateBarrierScreenThree(
                        viewModel = barrierUpdateViewModel,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onSubmitClick = {
                            navController.navigate(AppScreen.UpdateBarrierScreenFour.name)
                        }
                    )
                }

                composable(route = AppScreen.UpdateBarrierScreenFour.name) {
                    UpdateBarrierScreenFour(

                        onBackClick = {
                            navController.popBackStack()
                        },
                        onHomeClick = {
                            navController.navigate(AppScreen.Menu.name)
                        }
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
            OurBottomBar(
                buttons = listOf(
                    BottomBarButton(
                        icon = Icons.Filled.Apps,
                        contentDescription = "Menu Button",
                        onClickBehavior = {
                            navController.popBackStack(); navController.navigate(AppScreen.Menu.name)
                        }), //ToDo find better solution than calling popbackstack before navigating
                    BottomBarButton(
                        icon = Icons.Filled.Map,
                        contentDescription = "Map Button",
                        onClickBehavior = {
                            navController.popBackStack(); navController.navigate(AppScreen.Menu.name); navController.navigate(
                            AppScreen.Map.name
                        )
                        }),
                    BottomBarButton(
                        icon = Icons.Filled.AddLocationAlt,
                        contentDescription = "Report Button",
                        onClickBehavior = {
                            navController.popBackStack(); navController.navigate(AppScreen.Menu.name); navController.navigate(
                            AppScreen.Report.name
                        )
                        }),
                    BottomBarButton(
                        icon = Icons.Filled.EditLocationAlt,
                        contentDescription = "Update Barrier",
                        onClickBehavior = {
                            navController.popBackStack(); navController.navigate(AppScreen.Menu.name); navController.navigate(
                            AppScreen.UpdateBarrierScreenTwo.name
                        )
                        })

                )
            )
        }
    )
}