package com.example.team_koeln_bonn.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team_koeln_bonn.common.Resource
import com.example.team_koeln_bonn.domain.model.Barrier
import com.example.team_koeln_bonn.domain.model.UpdateAffectedGroup
import com.example.team_koeln_bonn.domain.use_case.get_barriers.SaveBarrierUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.collections.plus
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import android.util.Log

class BarrierReportViewModel (
    private val saveBarrierUseCase: SaveBarrierUseCase = SaveBarrierUseCase()
): ViewModel() {
    //holds the floating data we want to save into the database
    private val _barrierState = mutableStateOf(BarrierState())
    val barrierState: State<BarrierState> = _barrierState

    //creates a dummy object for testing the upload
    fun testCreateBarrier(){
        _barrierState.value.barrier = Barrier(
            coordinates = mutableListOf(51.023097, 7.562391),
            description = "Test Descript",
            tags = mutableListOf("Sehbeeinträchtigte", "Rollstuhl")
        )
    }

    fun saveBarrier(){
        //will save the current _barrierState to the database
        saveBarrierUseCase(_barrierState.value.barrier).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    // successfully saved the barrier
                    //ToDo proceed from a loading pop up
                }
                is Resource.Error -> {
                    _barrierState.value = BarrierState(
                        error = result.message ?: "An unexpected error occured"
                    )
                    //ToDo proceed from a loading pop up
                }
                is Resource.Loading -> {
                    _barrierState.value = BarrierState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    // Ausgewählte betroffene Personengruppen
    var selectedGroups by mutableStateOf(setOf<UpdateAffectedGroup>())
        private set

    // Beschreibung der Barriere
    var description by mutableStateOf("")
        private set

        //koordinaten GPS Ort ermitteln
    var latitude by mutableStateOf<Double?>(null)
            private set

    var longitude by mutableStateOf<Double?>(null)
        private set

    // Fügt eine Gruppe hinzu oder entfernt sie wieder
    fun toggleGroup(group: UpdateAffectedGroup) {
        selectedGroups =
            if (group in selectedGroups) {
                selectedGroups - group
            } else {
                selectedGroups + group
            }
    }

    // Aktualisiert die Beschreibung
    fun updateDescription(value: String) {
        description = value

        _barrierState.value.barrier.description = value
    }

    fun updateCoordinates(
        lat: Double,
        lon: Double
    ) {

        latitude = lat
        longitude = lon

        _barrierState.value.barrier.coordinates =
            mutableListOf(lat, lon)
    }

    fun prepareBarrier() {

        _barrierState.value.barrier.description = description

        _barrierState.value.barrier.coordinates = mutableListOf(
            latitude ?: 0.0,
            longitude ?: 0.0
        )

        _barrierState.value.barrier.tags =
            selectedGroups.map { it.name }.toMutableList()
    }


}
