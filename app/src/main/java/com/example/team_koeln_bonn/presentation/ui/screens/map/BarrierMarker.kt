package com.example.team_koeln_bonn.presentation.ui.screens.map

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.team_koeln_bonn.R
import com.example.team_koeln_bonn.domain.model.Barrier
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

// Erstellt einen Marker für eine Barriere
fun addBarrierMarker(
    mapView: MapView,
    barrier: Barrier,
    onBarrierClick: (Barrier) -> Unit
) {
    // Verhindert einen Absturz, falls keine vollständigen Koordinaten vorhanden sind
    if (barrier.coordinates.size < 2) {
        return
    }

    val barrierMarker = Marker(mapView)





    /*n(barrier.tags.size){
        0 -> barrierMarker.icon = ContextCompat.getDrawable( mapView.context,R.drawable.marker_placeholder_other)
        1 -> {
            when (barrier.tags[0]){
                "WALKING" -> barrierMarker.icon = ContextCompat.getDrawable( mapView.context,R.drawable.marker_placeholder_walk)
                "SEEING" -> barrierMarker.icon = ContextCompat.getDrawable( mapView.context,R.drawable.marker_placeholder_sight)
                "HEARING" -> barrierMarker.icon = ContextCompat.getDrawable( mapView.context,R.drawable.marker_placeholder_hear)


                else -> barrierMarker.icon = ContextCompat.getDrawable( mapView.context,R.drawable.marker_placeholder_other)
            }
        }
        else -> barrierMarker.icon = ContextCompat.getDrawable( mapView.context,R.drawable.marker_placeholder_mixed)
    } */

    barrierMarker.position = GeoPoint(
        barrier.coordinates[0],
        barrier.coordinates[1]
    )

    barrierMarker.title = barrier.description

    // Speichert die ID der Barriere im Marker,
    // damit Barrieremarker später gezielt entfernt werden können
    barrierMarker.relatedObject = barrier.id

    // Zeigt beim Klick die normale Marker-Sprechblase
    // und zusätzlich die Barrieredetails auf der Karte
    barrierMarker.setOnMarkerClickListener { marker, currentMapView ->
        onBarrierClick(barrier)

        // Öffnet die normale OSM-Info-Sprechblase mit dem Markertitel
        marker.showInfoWindow()

        // Zentriert die Karte auf den angeklickten Marker
        currentMapView.controller.animateTo(marker.position)

        true
    }

    mapView.overlays.add(barrierMarker)
}

// Entfernt alle bisherigen Barrieremarker von der Karte
fun removeBarrierMarkers(
    mapView: MapView
) {
    mapView.overlays.removeAll { overlay ->
        overlay is Marker &&
                overlay.relatedObject != null
    }
}

// Aktualisiert den Marker des aktuellen Nutzerstandorts
fun updateUserLocationMarker(
    mapView: MapView,
    latitude: Double,
    longitude: Double
) {
    // Entfernt den vorherigen Standortmarker,
    // damit immer nur die aktuelle Position angezeigt wird
    mapView.overlays.removeAll { overlay ->
        overlay is Marker &&
                overlay.title == "Aktueller Standort"
    }

    val userMarker = Marker(mapView)

    userMarker.position = GeoPoint(
        latitude,
        longitude
    )

    userMarker.title = "Aktueller Standort"

    mapView.overlays.add(userMarker)
}