package com.example.team_koeln_bonn.presentation.viewModel

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.team_koeln_bonn.domain.model.Barrier
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team_koeln_bonn.common.Resource
import com.example.team_koeln_bonn.data.remote.dto.BarrierDto
import com.example.team_koeln_bonn.data.remote.dto.toBarrier
import com.example.team_koeln_bonn.domain.use_case.get_barriers.GetBarriersUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.UUID

class BarrierListViewModel(
    private val getBarriersUseCase: GetBarriersUseCase = GetBarriersUseCase()
) : ViewModel() {

    private val _state = mutableStateOf(BarrierListState())
    val state: State<BarrierListState> = _state

    init {
        getBarriers()
    }

    fun updateBarriers(){
        getBarriers()
    }

    //ToDO: refresh is a current bandaid solution until mai brain figures out how to organize those asynchronous callbacks...
    //the issue is, that rn the solution in firestoreapiimpl suggests to have a callback as soon as the data is there. (hence why the propagate the "action" to refresh up until there
    //We need to somehow eliminate one of the asynchronous thingies I think
    fun refresh(barrierlist : List<BarrierDto>){
        _state.value = BarrierListState(barriers = barrierlist.map {it.toBarrier()} )
    }

    // Fügt eine neue Barriere zur lokalen Liste hinzu damit Marker sofort auf Karte sofort erscheint
    fun addBarrier(barrier: Barrier) {
        _state.value = _state.value.copy(
            barriers = _state.value.barriers + barrier
        )
    }

    //entfernt eine gelöschte barriere aus der lokalen liste damit marker von karte weg geht direkt
    fun removeBarrier(barrierId: UUID) {
        _state.value = _state.value.copy(
            barriers = _state.value.barriers.filterNot { barrier ->
                barrier.id == barrierId
            }
        )
    }

    //aktualisiert eine bearbeitete barriere in der lokalen liste damit die änderung sofort auf der karte sichtbar ist
    fun updateBarrierLocally(updatedBarrier: Barrier) {
        _state.value = _state.value.copy(
            barriers = _state.value.barriers.map { barrier ->
                if (barrier.id == updatedBarrier.id) {
                    updatedBarrier
                } else {
                    barrier
                }
            }
        )
    }

    private fun getBarriers() {
        getBarriersUseCase(action = ::refresh).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = BarrierListState(barriers = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = BarrierListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = BarrierListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}