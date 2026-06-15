package com.example.team_koeln_bonn.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
@Preview
fun DisplayMapScreen(){
    MapScreen()
}

@Composable
fun MapScreen(modifier : Modifier = Modifier){
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

/*
fun MapScreen(){
    Image(
        painter = painterResource((R.drawable.map_placeholder.png)),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}
*/