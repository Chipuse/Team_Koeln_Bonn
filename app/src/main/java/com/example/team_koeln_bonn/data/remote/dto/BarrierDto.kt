package com.example.team_koeln_bonn.data.remote.dto

import com.example.team_koeln_bonn.domain.model.Barrier

data class BarrierDto(
    val id: String,
    val coordinates: List<Double>,
    val description: String,
    val tags: List<String>
    //image?
)

fun BarrierDto.toBarrier(): Barrier {
    return Barrier(
        id = id,
        coordinates = coordinates,
        description = description,
        tags = tags
    )
}