package com.example.team_koeln_bonn.domain.model

data class Barrier(
    val id: String,
    val coordinates: List<Double>,
    val description: String,
    val tags: List<String>
)
