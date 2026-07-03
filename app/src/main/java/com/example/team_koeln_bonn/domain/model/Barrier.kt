package com.example.team_koeln_bonn.domain.model

import java.util.UUID

data class Barrier(
    val id: UUID = UUID.randomUUID(),
    var coordinates: MutableList<Double> = mutableListOf(),
    var description: String = "",
    var tags: MutableList<String> = mutableListOf()
)
