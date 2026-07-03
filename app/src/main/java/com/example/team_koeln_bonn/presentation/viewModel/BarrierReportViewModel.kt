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
    }
}