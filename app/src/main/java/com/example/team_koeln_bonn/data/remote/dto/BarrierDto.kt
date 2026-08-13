package com.example.team_koeln_bonn.data.remote.dto

import com.example.team_koeln_bonn.domain.model.Barrier
import java.util.UUID

data class BarrierDto(
    val id: String,
    val lon : Double,
    val lat : Double,
    val description: String,
    val tags: List<String>
    //image?
)

fun BarrierDto.toBarrier(): Barrier {

    val barrier =  Barrier(
        id = UUID.fromString(id),
        description = description,
        tags = tags as MutableList<String>
    )
    barrier.coordinates.add(lon)
    barrier.coordinates.add(lat)
    return barrier
}

fun Barrier.toBarrierDto(): BarrierDto{
    return BarrierDto(
        id = id.toString(),
        lon = coordinates[0],
        lat = coordinates[1],
        description = description,
        tags = tags
    )
}