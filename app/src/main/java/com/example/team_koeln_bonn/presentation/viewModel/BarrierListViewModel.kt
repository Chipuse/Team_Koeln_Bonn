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
import com.example.team_koeln_bonn.data.remote.dto.toBarrierDto
import com.example.team_koeln_bonn.data.repository.BarrierRepositoryImpl
import com.example.team_koeln_bonn.domain.repository.BarrierRepository
import com.example.team_koeln_bonn.domain.use_case.get_barriers.GetBarriersInAreaUseCase
import com.example.team_koeln_bonn.domain.use_case.get_barriers.GetBarriersUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.abs

class BarrierListViewModel(
    private val getBarriersUseCase: GetBarriersUseCase = GetBarriersUseCase(),
    private val getBarriersInAreaUseCase: GetBarriersInAreaUseCase = GetBarriersInAreaUseCase()
) : ViewModel() {

    //
    private val _downloadState = mutableStateOf(BarrierListState())

    //contains current list of all barriers on the phone
    private val _state = mutableStateOf(listOf<Barrier>())
    val state: State<List<Barrier>> = _state



    fun updateBarriers(centerCoordinates : List<Double> = listOf<Double>(50.96, 7.00), areaRadius: Double = 0.05){
        getBarriersInArea(centerCoordinates, areaRadius)
    }


    fun refresh(barrierlist : List<BarrierDto>){
        var currentBarriers : MutableList<Barrier> = _state.value.toMutableList()
        val newBarriers = barrierlist.map {it.toBarrier()}
        //put all fetched entries in. Same UUID gets overwritten with info fetched from server to make sure we have up to date info about our barriers.
        newBarriers.forEach { barrier ->
            currentBarriers.remove(currentBarriers.find { it.id == barrier.id })
            currentBarriers.add(barrier)
        }

        _state.value = currentBarriers.toList()
    }

    // Fügt eine neue Barriere zur lokalen Liste hinzu damit Marker sofort auf Karte sofort erscheint
    fun addBarrier(barrier: Barrier) {
        _downloadState.value = _downloadState.value.copy(
            barriers = _downloadState.value.barriers + barrier
        )
    }

    //entfernt eine gelöschte barriere aus der lokalen liste damit marker von karte weg geht direkt
    fun removeBarrier(barrierId: UUID) {
        _downloadState.value = _downloadState.value.copy(
            barriers = _downloadState.value.barriers.filterNot { barrier ->
                barrier.id == barrierId
            }
        )
        _state.value = _state.value.filterNot { barrier ->
            barrier.id == barrierId
        }
    }

    //aktualisiert eine bearbeitete barriere in der lokalen liste damit die änderung sofort auf der karte sichtbar ist
    fun updateBarrierLocally(updatedBarrier: Barrier) {
        _downloadState.value = _downloadState.value.copy(
            barriers = _downloadState.value.barriers.map { barrier ->
                if (barrier.id == updatedBarrier.id) {
                    updatedBarrier
                } else {
                    barrier
                }
            }
        )
        _state.value = _state.value.map{ barrier ->
            if (barrier.id == updatedBarrier.id) {
                updatedBarrier
            } else {
                barrier
            }
        }
    }

    private fun getBarriersInArea(centerCoordinates : List<Double>, areaRadius : Double) {
        getBarriersInAreaUseCase(action = ::refresh, listOf<Double>(50.95, 7.0), areaRadius).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    //_state.value = BarrierListState(barriers = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _downloadState.value = BarrierListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _downloadState.value = BarrierListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getBarriers() {
        getBarriersUseCase(action = ::refresh).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    //_state.value = BarrierListState(barriers = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _downloadState.value = BarrierListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _downloadState.value = BarrierListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    //Bulk insertion of Barriers for Scope Demonstration and testing. Not for Final Build!
    fun initBarrierCluster(){
        viewModelScope.launch {
            CreateBarrierCluster(
                centerCoordinates = listOf<Double>(40.774611, -73.979274),
                areaRadius = 0.02
            )
        }
    }

    private suspend fun CreateBarrierCluster(centerCoordinates: List<Double>, areaRadius: Double, steplength : Double = areaRadius / 4){
        val repository: BarrierRepository = BarrierRepositoryImpl()
        var Counter = 1
        var i = centerCoordinates[0] - abs(areaRadius)
        while (i < centerCoordinates[0] + abs(areaRadius)){
            var j = centerCoordinates[1] - abs(areaRadius)
            while (j < centerCoordinates[1] + abs(areaRadius)){
                val newBarrier = Barrier(
                    coordinates = mutableListOf<Double>(
                        i,
                        j,
                    ),
                    description = "NY Centrum $Counter: $i Lon und $j Lat",
                    tags = mutableListOf<String>("OTHER")
                )
                Counter++
                repository.saveBarrier(newBarrier.toBarrierDto())

                j += abs(steplength)
            }
            i += abs(steplength)
        }
    }
}