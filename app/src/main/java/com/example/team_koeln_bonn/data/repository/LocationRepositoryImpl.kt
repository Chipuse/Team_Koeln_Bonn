package com.example.team_koeln_bonn.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.example.team_koeln_bonn.domain.model.UserLocation
import com.example.team_koeln_bonn.domain.repository.LocationRepository
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

// Implementiert den Zugriff auf den GPS-Standort.
// Später wird hier zusätzlich der Backend-Aufruf ergänzt.

class LocationRepositoryImpl(
    private val context: Context) : LocationRepository {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context) //GPS-Client vom Google Dienst


    //regelmäßige Standortupdates
    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(
        interval: Long, //Zeit zwischen den Standortabfragen (Millisekunden)
        minUpdateDistance: Float //Mindestbewegung (in Metern) bevor neuer Standort geliefert wird
    ): Flow<UserLocation> = callbackFlow {


        //Erstellung der Konfiguration für GPS-Tracking
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, //verwendet möglichst GPS und liefert genauen Standort
            interval //wie oft nach Standort suchen
        )
            .setMinUpdateDistanceMeters(minUpdateDistance) //Nutzer muss sich mindestens diese Anzahl an Meter bewegen
            .build()

        //Callback wird automatisch aufgerufen, wenn neuen GPS-Standort liefert
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                   //Erstellt ein UserLocation-Objekt und sendet aktuellen Standort an ViewModel
                    trySend(
                        UserLocation(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )
                }
            }
        }

        //GPS-Dienst von Google, dafür zuständig den aktuellen Standort zu bestimmen (GPS,WLAN und Mobilfunk miteinander zu kombinieren)
        fusedLocationClient.requestLocationUpdates(
            request,
            callback,
            Looper.getMainLooper()
        )

        //Beendet das GPS-Tracking
        awaitClose {
            fusedLocationClient.removeLocationUpdates(callback)
        }
    }

    //Später wird hier Standort an Backend geschickt und gespeichert
    override suspend fun saveLocation(location: UserLocation) {
        println("Standort für Backend vorbereitet: ${location.latitude}, ${location.longitude}")
    }
}