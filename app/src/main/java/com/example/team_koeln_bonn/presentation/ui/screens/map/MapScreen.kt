package com.example.team_koeln_bonn.presentation.ui.screens.map

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.team_koeln_bonn.data.remote.BarrierDatabaseApi
import com.example.team_koeln_bonn.data.remote.BarrierDatabaseApiMockup
import com.example.team_koeln_bonn.data.repository.BarrierRepositoryImpl
import com.example.team_koeln_bonn.domain.repository.BarrierRepository
import com.example.team_koeln_bonn.domain.use_case.get_barriers.GetBarriersUseCase
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

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MapScreen(
    modifier : Modifier = Modifier,
    navController: NavController,
    viewModel: BarrierListViewModel = BarrierListViewModel()
){
    //da die osm library nur views unterstützt müssen wir per android view das einfügen:
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val mapView = MapView(context)
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setMultiTouchControls(true)
            mapView.controller.setZoom(15.0)

            val startPoint = GeoPoint(51.023097, 7.562391)
            mapView.controller.setCenter(startPoint)

            var startMarker = Marker(mapView)
            startMarker.position = startPoint
            startMarker.title = "Start Position"

            mapView.overlays.add(startMarker)
            mapView
        }
    )
}