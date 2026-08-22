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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.team_koeln_bonn.presentation.ui.composables.BottomBarButton
import com.example.team_koeln_bonn.presentation.ui.theme.AppBlue
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.views.overlay.MapEventsOverlay
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Checkbox
import android.R.attr.checked


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
    //locationViewModel: LocationViewModel
) {
    val context = LocalContext.current

    val allBarriers = barrierListViewModel.state.value

    // Speichert die Barriere, auf die der Nutzer geklickt hat
    var selectedBarrier by remember {
        mutableStateOf<Barrier?>(null)
    }

    //Filter
    var showFilterDialog by remember {
        mutableStateOf(false)
    }

    var selectedFilters by remember {
        mutableStateOf(setOf<String>())
    }

    var appliedFilters by remember {
        mutableStateOf(setOf<String>())
    }

    //Barrieren anhand der angewendeten Filter filtern
    val filteredBarriers =
        allBarriers.filter { barrier ->
        appliedFilters.isEmpty() ||
                barrier.tags.any { tag ->
                    tag in appliedFilters
                }
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

    //why do we create viewmodels inside our views???
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

    //OSM Droid Map Controller
    val mapView = remember {
        MapView(context)
    }

    // Enthält die Karte und die Barrieredetails, die über der Karte angezeigt werden
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        // Da die OSM-Library nur Views unterstützt, wird die Karte über AndroidView eingefügt
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->

                mapView.setTileSource(CustomTileSource())//TileSourceFactory.OpenTopo
                mapView.setMultiTouchControls(true)
                mapView.controller.setZoom(15.0)

                //val barrierClickEventListener = OnBarrierClick(navController)

                //ToDo Start on user position and not in gummersbach. Also button for recentering and local download
                val startPoint = GeoPoint(
                    50.941479, 6.959103
                )
                userLocation?.let { location ->
                    {
                        updateUserLocationMarker(
                            mapView = mapView,
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                        startPoint.latitude = location.latitude
                        startPoint.longitude = location.longitude
                    }

                }

                mapView.controller.setCenter(startPoint)

                // Erstellt für jede gespeicherte Barriere einen Marker
                filteredBarriers.forEach { barrier ->
                    addBarrierMarker(
                        mapView = mapView,
                        barrier = barrier,
                        onBarrierClick = { clickedBarrier ->
                            selectedBarrier = clickedBarrier
                            showBarrierDetails = true
                        }
                    )
                }

                //val startMarker = Marker(mapView)
                //startMarker.position = startPoint
                //startMarker.title = "Start Position"
                //startMarker.setOnMarkerClickListener(barrierClickEventListener)
                //mapView.overlays.add(startMarker)
                val tapOverlay = MapEventsOverlay(object : MapEventsReceiver {
                    override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {

                        return true
                    }

                    override fun longPressHelper(p: GeoPoint?): Boolean {
                        // Trigger Menu to add new Barrier at location
                        if (p == null)
                            return true

                        val newBarrier = Barrier(
                            coordinates = mutableListOf<Double>(
                                p.latitude, p.longitude
                            ),
                            description = "Eine neue Barriere wurde an diesem Standort hinzugefügt. Bitte Updaten Sie die Informationen oder nutzen Sie 'Barriere Löschen' um den Vorgang abzubrechen."

                        )

                        addBarrierMarker(
                            mapView = mapView,
                            barrier = newBarrier,
                            onBarrierClick = { clickedBarrier ->
                                selectedBarrier = clickedBarrier
                                showBarrierDetails = true
                            }
                        )
                        barrierListViewModel.addBarrier(newBarrier)
                        selectedBarrier = newBarrier
                        showBarrierDetails = true

                        return true
                    }
                })
                mapView.overlays.add(tapOverlay)

                mapView
            },

            // Aktualisiert die Marker auf der Karte,
            // sobald sich die Barriereliste oder die GPS-Daten ändern
            update = { mapView ->

                // Entfernt alle bisherigen Barrieremarker,
                // damit gelöschte und bearbeitete Barrieren sofort aktualisiert werden
                removeBarrierMarkers(mapView)

                // Erstellt die Barrieremarker mit den aktuellen Daten neu
                filteredBarriers.forEach { barrier ->
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

        //floating buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            //button for recentering on current location
            SmallFloatingActionButton(
                onClick = {
                    barrierListViewModel.updateBarriers(
                        listOf<Double>(mapView.mapCenter.latitude, mapView.mapCenter.longitude),
                        maxOf(mapView.longitudeSpanDouble, mapView.latitudeSpanDouble)

                    )
                    mapView.postInvalidate()
                },

                ) {
                Icon(Icons.Filled.CloudDownload, "Download Local Barriers")
            }
            Spacer(
                modifier = Modifier.padding(16.dp)
            )
            FloatingActionButton(
                onClick = {
                    userLocation?.let { location ->
                        val userLocation = GeoPoint(
                            location.latitude,
                            location.longitude
                        )
                        mapView.controller.zoomTo(17.0)
                        mapView.controller.animateTo(userLocation)
                    }
                },
            ) {
                Icon(Icons.Filled.Adjust, "Recenter on User Location")
            }

            Spacer(
                modifier = Modifier.padding(8.dp)
            )

            //Filter
            FloatingActionButton(
                onClick = {
                    showFilterDialog = true
                }
            ) {
                Icon(
                    Icons.Filled.Tune,
                    contentDescription = "Filter"
                )
            }

        }


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

        //Filter Popup
        if (showFilterDialog) {
            AlertDialog(
                onDismissRequest = {
                    showFilterDialog = false
                },

                title = {
                    Text("Filter")
                },

                text = {
                    Column {

                        Text("Nach welchen Barrieren filtern?")

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = "SEEING" in selectedFilters,
                                onCheckedChange = { checked ->
                                    selectedFilters =
                                        if (checked) {
                                            selectedFilters + "SEEING"
                                        } else {
                                            selectedFilters - "SEEING"
                                        }
                                }
                            )

                            Text("Sehbeeinträchtigte")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = "WALKING" in selectedFilters,
                                onCheckedChange = { checked ->
                                    selectedFilters =
                                        if (checked) {
                                            selectedFilters + "WALKING"
                                        } else {
                                            selectedFilters - "WALKING"
                                        }
                                }
                            )

                            Text("Gehbeeinträchtigte")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = "HEARING" in selectedFilters,
                                onCheckedChange = { checked ->
                                    selectedFilters =
                                        if (checked) {
                                            selectedFilters + "HEARING"
                                        } else {
                                            selectedFilters - "HEARING"

                                        }
                                }
                            )

                            Text("Hörbeeinträchtigte")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = "OTHER" in selectedFilters,
                                onCheckedChange = { checked ->
                                    selectedFilters =
                                        if (checked) {
                                            selectedFilters + "OTHER"
                                        } else {
                                            selectedFilters - "OTHER"
                                        }
                                }
                            )

                            Text("Sonstiges")
                        }
                    }
                },

                confirmButton = {
                    TextButton(
                        onClick = {
                            appliedFilters = selectedFilters
                            showFilterDialog = false
                        }
                    ) {
                        Text("Filtern")
                    }
                }
            )
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
    }
}