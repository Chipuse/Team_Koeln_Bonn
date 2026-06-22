package com.example.team_koeln_bonn.presentation.viewModel

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team_koeln_bonn.common.Resource
import com.example.team_koeln_bonn.domain.use_case.get_barriers.GetBarriersUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class BarrierListViewModel (
    private val getBarriersUseCase: GetBarriersUseCase = GetBarriersUseCase()
) : ViewModel() {
    //ToDo mai left off here: https://www.youtube.com/watch?v=EF33KmyprEQ Timestamp 49:19
    private val _state = mutableStateOf(BarrierListState())
    val state: State<BarrierListState> = _state

    init {
        getBarriers()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun getBarriers() {
        getBarriersUseCase().onEach { result ->
            when (result){
                is Resource.Success -> {
                    _state.value = BarrierListState(barriers = result.data ?: emptyList())

                }
                is Resource.Error -> {
                    _state.value = BarrierListState(error = result.message ?: "An unknown Error happened")

                }
                is Resource.Loading -> {
                    _state.value = BarrierListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}