package com.example.team_koeln_bonn.domain.model

import java.util.UUID
import kotlin.compareTo

data class Barrier(
    val id: UUID = UUID.randomUUID(),
    var coordinates: MutableList<Double> = mutableListOf(),
    var description: String = "",
    var tags: MutableList<String> = mutableListOf()


) : Comparable<Barrier>
{
    override fun compareTo(other: Barrier): Int {
        return this.id.compareTo(other.id)
    }
}
