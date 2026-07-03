package com.example.team_koeln_bonn.presentation.viewModel

import com.example.team_koeln_bonn.domain.model.Barrier

data class BarrierState (
    val isLoading: Boolean = false,
    var barrier: Barrier = Barrier(),
    val error : String = ""
)