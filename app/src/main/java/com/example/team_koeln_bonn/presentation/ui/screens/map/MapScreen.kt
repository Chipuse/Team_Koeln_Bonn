package com.example.team_koeln_bonn.presentation.ui.screens.map

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.team_koeln_bonn.presentation.ui.screens.AppScreen
import com.example.team_koeln_bonn.presentation.viewModel.BarrierListState
import com.example.team_koeln_bonn.presentation.viewModel.BarrierListViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
@Preview
fun DisplayMapScreen(){
    //MapScreen()
}

@Composable
fun MapScreen(
    modifier : Modifier = Modifier,
    navController: NavController,
    barrierListViewModel: BarrierListViewModel = BarrierListViewModel()
    ){
    //da die osm library nur views unterstützt müssen wir per android view das einfügen:
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val mapView = MapView(context)
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setMultiTouchControls(true)
            mapView.controller.setZoom(15.0)
            val barrierClickEventListener = OnBarrierClick(navController)
            val startPoint = GeoPoint(51.023097, 7.562391)
            mapView.controller.setCenter(startPoint)
            //ToDo barrierListViewModel.state.value.barriers
            var startMarker = Marker(mapView)
            startMarker.position = startPoint
            startMarker.title = "Start Position"
            startMarker.setOnMarkerClickListener(barrierClickEventListener)
            mapView.overlays.add(startMarker)
            mapView
        }
    )
}

fun AddBarrierMarker(){

}

class OnBarrierClick(val navController : NavController) : Marker.OnMarkerClickListener{

    override fun onMarkerClick(
        p0: Marker?,
        p1: MapView?
    ): Boolean {
        navController.navigate(AppScreen.UpdateBarrierScreenTwo.name);
        return true
    }

}