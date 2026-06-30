package com.example.team_koeln_bonn.domain.repository

import com.example.team_koeln_bonn.domain.model.UserLocation
import kotlinx.coroutines.flow.Flow

// Schnittstelle für den Zugriff auf Standortdaten.
interface LocationRepository {

    // Liefert regelmäßige Standortupdates vom Gerät.
    fun getLocationUpdates(
        interval: Long,
        minUpdateDistance: Float = 5f
    ): Flow<UserLocation>

    // Speichert den aktuellen Standort.
    suspend fun saveLocation(location: UserLocation)

    class LocationException(message: String) : Exception(message)
}