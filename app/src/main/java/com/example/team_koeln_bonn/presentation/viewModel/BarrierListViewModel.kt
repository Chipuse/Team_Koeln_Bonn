package com.example.team_koeln_bonn.presentation.viewModel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team_koeln_bonn.common.Resource
import com.example.team_koeln_bonn.domain.use_case.get_barriers.GetBarriersUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BarrierListViewModel (
    private val getBarriersUseCase: GetBarriersUseCase = GetBarriersUseCase()
) : ViewModel() {
    private val _state = mutableStateOf(BarrierListState())
    val state: State<BarrierListState> = _state

    init {
        getBarriers()
    }

    private fun getBarriers() {
        getBarriersUseCase().onEach { result ->
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