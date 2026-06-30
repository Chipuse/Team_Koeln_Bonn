package com.example.team_koeln_bonn.presentation.ui.screens.map

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.team_koeln_bonn.data.repository.LocationRepositoryImpl
import com.example.team_koeln_bonn.presentation.ui.screens.AppScreen
import com.example.team_koeln_bonn.presentation.viewModel.BarrierListViewModel
import com.example.team_koeln_bonn.presentation.viewModel.LocationViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
@Preview
fun DisplayMapScreen() {
    //MapScreen()
}

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    barrierListViewModel: BarrierListViewModel
) {
    val context = LocalContext.current


    // GPS-Repository und ViewModel
    val locationRepository = remember {
        LocationRepositoryImpl(context)
    }

    val locationViewModel = remember {
        LocationViewModel(locationRepository)
    }

    val userLocation by locationViewModel.userLocation.collectAsState()

    // Android-Standortberechtigung anfragen
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) {
            locationViewModel.startLocationTracking()
        }
    }



    // da die osm library nur views unterstützt müssen wir per android view das einfügen:
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            val mapView = MapView(context)
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setMultiTouchControls(true)
            mapView.controller.setZoom(15.0)
            val barrierClickEventListener = OnBarrierClick(navController)
            val startPoint = GeoPoint(51.023097, 7.562391)
            mapView.controller.setCenter(startPoint)
            // ToDo barrierListViewModel.state.value.barriers
            val startMarker = Marker(mapView)
            startMarker.position = startPoint
            startMarker.title = "Start Position"
            startMarker.setOnMarkerClickListener(barrierClickEventListener)
            mapView.overlays.add(startMarker)
            mapView
        },

        // Aktualisiert den Marker des aktuellen Nutzerstandorts, sobald neue GPS-Daten verfügbar sind
        update = { mapView ->
            userLocation?.let { location ->
                val userPoint = GeoPoint(location.latitude, location.longitude)

                // Entfernt vorherigen Standortmarker, damit immer aktuelle Position angezeigt wird
                mapView.overlays.removeAll { overlay ->
                    overlay is Marker && overlay.title == "Aktueller Standort"
                }

                // Erstellt neuen Marker für aktuellen Standort auf der Karte
                val userMarker = Marker(mapView)
                userMarker.position = userPoint
                userMarker.title = "Aktueller Standort"

                mapView.overlays.add(userMarker)
                mapView.invalidate()
            }
        }
    )
}

fun AddBarrierMarker() {

}

class OnBarrierClick(val navController: NavController) : Marker.OnMarkerClickListener {

    override fun onMarkerClick(
        p0: Marker?,
        p1: MapView?
    ): Boolean {
        navController.navigate(AppScreen.UpdateBarrierScreenTwo.name)
        return true
    }
}