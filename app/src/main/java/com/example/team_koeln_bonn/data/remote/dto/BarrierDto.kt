package com.example.team_koeln_bonn.data.remote.dto

import com.example.team_koeln_bonn.domain.model.Barrier
import java.util.UUID

data class BarrierDto(
    val id: String,
    val coordinates: List<Double>,
    val description: String,
    val tags: List<String>
    //image?
)

fun BarrierDto.toBarrier(): Barrier {
    return Barrier(
        id = UUID.fromString(id),
        coordinates = coordinates as MutableList<Double>,
        description = description,
        tags = tags as MutableList<String>
    )
}

fun Barrier.toBarrierDto(): BarrierDto{
    return BarrierDto(
        id = id.toString(),
        coordinates = coordinates,
        description = description,
        tags = tags
    )
}