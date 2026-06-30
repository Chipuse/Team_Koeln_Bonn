package com.example.team_koeln_bonn.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team_koeln_bonn.domain.model.UserLocation
import com.example.team_koeln_bonn.domain.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _userLocation = MutableStateFlow<UserLocation?>(null)
    val userLocation: StateFlow<UserLocation?> = _userLocation

    // Startet das GPS-Tracking und aktualisiert den aktuellen Standort
    fun startLocationTracking() {
        viewModelScope.launch {
            locationRepository.getLocationUpdates(
                interval = 5000L,
                minUpdateDistance = 5f
            ).collect { location ->
                _userLocation.value = location
                locationRepository.saveLocation(location)
            }
        }
    }
}