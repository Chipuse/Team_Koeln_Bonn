package com.example.team_koeln_bonn.domain.model

//Das ist das Datenmodell für den Standort des Nutzers, speichert aktuelle Standortdaten in nem Objekt
//damit in der App es später weitergegeben wird und an das Backend gesendet werden kann

data class UserLocation(
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long = System.currentTimeMillis()
)