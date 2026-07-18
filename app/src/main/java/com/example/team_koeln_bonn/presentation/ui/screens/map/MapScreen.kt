package com.example.team_koeln_bonn.presentation.ui.screens.map

import androidx.compose.runtime.LaunchedEffect
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
import com.example.team_koeln_bonn.domain.model.Barrier
import com.example.team_koeln_bonn.presentation.ui.screens.AppScreen
import com.example.team_koeln_bonn.presentation.viewModel.BarrierListViewModel
import com.example.team_koeln_bonn.presentation.viewModel.BarrierUpdateViewModel
import com.example.team_koeln_bonn.presentation.viewModel.LocationViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.team_koeln_bonn.presentation.ui.theme.AppBlue


@Composable
@Preview
fun DisplayMapScreen() {
    //MapScreen()
}

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    barrierListViewModel: BarrierListViewModel,
    barrierUpdateViewModel: BarrierUpdateViewModel
)

 {
    val context = LocalContext.current
    val barriers = barrierListViewModel.state.value.barriers

    // Speichert die Barriere, auf die der Nutzer geklickt hat
    var selectedBarrier by remember {
        mutableStateOf<Barrier?>(null)
    }

    // Steuert, ob die Barrieredetails auf der Karte angezeigt werden
    var showBarrierDetails by remember {
        mutableStateOf(false)
    }

    // Steuert, ob der Bestätigungsdialog zum Löschen angezeigt wird
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

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

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    // Enthält die Karte und die Barrieredetails, die über der Karte angezeigt werden
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        // Da die OSM-Library nur Views unterstützt, wird die Karte über AndroidView eingefügt
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val mapView = MapView(context)

                mapView.setTileSource(TileSourceFactory.MAPNIK)
                mapView.setMultiTouchControls(true)
                mapView.controller.setZoom(15.0)

                val barrierClickEventListener = OnBarrierClick(navController)

                val startPoint = GeoPoint(
                    51.023097,
                    7.562391
                )

                mapView.controller.setCenter(startPoint)

                // Erstellt für jede gespeicherte Barriere einen Marker
                barriers.forEach { barrier ->
                    addBarrierMarker(
                        mapView = mapView,
                        barrier = barrier,
                        onBarrierClick = { clickedBarrier ->
                            selectedBarrier = clickedBarrier
                            showBarrierDetails = true
                        }
                    )
                }

                val startMarker = Marker(mapView)
                startMarker.position = startPoint
                startMarker.title = "Start Position"
                startMarker.setOnMarkerClickListener(barrierClickEventListener)

                mapView.overlays.add(startMarker)

                mapView
            },

            // Aktualisiert die Marker auf der Karte,
            // sobald sich die Barriereliste oder die GPS-Daten ändern
            update = { mapView ->

                // Entfernt alle bisherigen Barrieremarker,
                // damit gelöschte und bearbeitete Barrieren sofort aktualisiert werden
                removeBarrierMarkers(mapView)

                // Erstellt die Barrieremarker mit den aktuellen Daten neu
                barriers.forEach { barrier ->
                    addBarrierMarker(
                        mapView = mapView,
                        barrier = barrier,
                        onBarrierClick = { clickedBarrier ->
                            selectedBarrier = clickedBarrier
                            showBarrierDetails = true
                        }
                    )
                }

                userLocation?.let { location ->
                    updateUserLocationMarker(
                        mapView = mapView,
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                }

                mapView.invalidate()
            }
        )

        // Zeigt die Informationen der ausgewählten Barriere über der Karte an
        selectedBarrier?.let { barrier ->
            if (showBarrierDetails) {
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppBlue
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Barriereinformationen"
                        )

                        Spacer(
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Text(
                            text = barrier.description.ifBlank {
                                "Für diese Barriere ist keine Beschreibung vorhanden."
                            }
                        )

                        Spacer(
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = {
                                    // Schließt die Barrieredetails
                                    showBarrierDetails = false
                                }
                            ) {
                                Text("Schließen")
                            }

                            Spacer(
                                modifier = Modifier.weight(1f)
                            )

                            Button(
                                onClick = {
                                    // Übergibt die ausgewählte Barriere
                                    // an das Update-ViewModel
                                    barrierUpdateViewModel.initUpdate(barrier)

                                    // Öffnet den ersten Bearbeitungs-Screen
                                    navController.navigate(
                                        AppScreen.UpdateBarrierScreenTwo.name
                                    )
                                }
                            ) {
                                Text("Barriere updaten")
                            }
                        }

                        // Öffnet den Bestätigungsdialog zum Löschen
                        TextButton(
                            onClick = {
                                showDeleteDialog = true
                            }
                        ) {
                            Text("Barriere löschen")
                        }
                    }
                }
            }
        }

        // Fragt vor dem Löschen noch einmal nach einer Bestätigung
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteDialog = false
                },
                title = {
                    Text("Barriere löschen")
                },
                text = {
                    Text(
                        "Möchtest du diese Barriere wirklich löschen?"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedBarrier?.let { barrier ->
                                // Übergibt die ausgewählte Barriere
                                // an das Update-ViewModel
                                barrierUpdateViewModel.initUpdate(barrier)

                                // Löscht die ausgewählte Barriere
                                barrierUpdateViewModel.deleteBarrier { deletedBarrier ->

                                    // Entfernt die Barriere auch aus der lokalen Liste,
                                    // damit der Marker sofort von der Karte verschwindet
                                    barrierListViewModel.removeBarrier(
                                        deletedBarrier.id
                                    )

                                    // Schließt den Dialog und die Barrieredetails
                                    showDeleteDialog = false
                                    showBarrierDetails = false
                                    selectedBarrier = null
                                }
                            }
                        }
                    ) {
                        Text("Löschen")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                        }
                    ) {
                        Text("Abbrechen")
                    }
                }
            )
        }
    }
}

class OnBarrierClick(
    val navController: NavController
) : Marker.OnMarkerClickListener {

    override fun onMarkerClick(
        p0: Marker?,
        p1: MapView?
    ): Boolean {
        navController.navigate(
            //ToDO set updateviewmodel init update barrier
            AppScreen.UpdateBarrierScreenTwo.name
        )

        return true
    }
}