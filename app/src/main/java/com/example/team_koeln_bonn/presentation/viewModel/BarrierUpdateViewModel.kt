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
import com.example.team_koeln_bonn.domain.use_case.get_barriers.DeleteBarrierUseCase
import com.example.team_koeln_bonn.domain.use_case.get_barriers.UpdateBarrierUseCase

class BarrierUpdateViewModel (
    private val saveBarrierUseCase: SaveBarrierUseCase = SaveBarrierUseCase(),
    private val updateBarrierUseCase: UpdateBarrierUseCase = UpdateBarrierUseCase(),
    private val deleteBarrierUseCase: DeleteBarrierUseCase = DeleteBarrierUseCase()

): ViewModel() {
    //compared to the reportviewmodel, here we start the viewmodel immediately with the barrier we want to edit
    //holds the floating data we want to save into the database. _barrierstate.barrier needs to be edited by the other functions of this viewmodel!
    fun initUpdate(barrier: Barrier) {
        _barrierState.value = _barrierState.value.copy(
            barrier = barrier
        )

        description = barrier.description

        selectedGroups = barrier.tags
            .mapNotNull { tag ->
                UpdateAffectedGroup.entries.find { group ->
                    group.name == tag
                }
            }
            .toSet()
    }

    private val _barrierState = mutableStateOf(BarrierState())
    val barrierState: State<BarrierState> = _barrierState

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

    // Aktualisiert die aktuelle Barriere in Firestore
    fun updateBarrier(
        onSuccess: (Barrier) -> Unit
    ) {
        updateBarrierUseCase(_barrierState.value.barrier).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    // Barriere wurde erfolgreich aktualisiert
                    result.data?.let { updatedBarrier ->
                        onSuccess(updatedBarrier)
                    }

                    _barrierState.value = _barrierState.value.copy(
                        isLoading = false,
                        error = ""
                    )

                    //ToDo Ladeanzeige schließen oder zur Karte zurückkehren
                }

                is Resource.Error -> {
                    _barrierState.value = BarrierState(
                        barrier = _barrierState.value.barrier,
                        error = result.message ?: "Beim Aktualisieren ist ein Fehler aufgetreten"
                    )
                }

                is Resource.Loading -> {
                    _barrierState.value = BarrierState(
                        barrier = _barrierState.value.barrier,
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    // Löscht die aktuelle Barriere anhand ihrer ID aus Firestore
    fun deleteBarrier(
        onSuccess: (Barrier) -> Unit
    ) {
        val barrierToDelete = _barrierState.value.barrier

        deleteBarrierUseCase(barrierToDelete.id.toString()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    // Barriere wurde erfolgreich gelöscht
                    onSuccess(barrierToDelete)

                    _barrierState.value = _barrierState.value.copy(
                        isLoading = false,
                        error = ""
                    )

                    //ToDo Ladeanzeige schließen oder zur Karte zurückkehren
                }

                is Resource.Error -> {
                    _barrierState.value = BarrierState(
                        barrier = _barrierState.value.barrier,
                        error = result.message ?: "Beim Löschen ist ein Fehler aufgetreten"
                    )
                }

                is Resource.Loading -> {
                    _barrierState.value = BarrierState(
                        barrier = _barrierState.value.barrier,
                        isLoading = true
                    )
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

        _barrierState.value = _barrierState.value.copy(
            barrier = _barrierState.value.barrier.copy(
                tags = selectedGroups
                    .map { it.name }
                    .toMutableList()
            )
        )
    }

    // Aktualisiert die Beschreibung
    fun updateDescription(value: String) {
        description = value

        _barrierState.value = _barrierState.value.copy(
            barrier = _barrierState.value.barrier.copy(
                description = value
            )
        )
    }
}