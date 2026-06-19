package com.example.team_koeln_bonn.presentation.viewModel

import com.example.team_koeln_bonn.domain.model.Barrier

data class BarrierListState(
    val isLoading: Boolean = false,
    val barriers : List<Barrier> = emptyList(),
    val error : String = ""
)
