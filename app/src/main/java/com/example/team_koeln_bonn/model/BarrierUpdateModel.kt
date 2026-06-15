package com.example.team_koeln_bonn.model

enum class UpdateAffectedGroup(val label: String) {
    WALKING("Gehbeeinträchtigte"),
    SEEING("Sehbeeinträchtigte"),
    HEARING("Hörbeeinträchtigte"),
    OTHER("Sonstiges")
}

data class BarrierUpdate(
    val affectedGroups: List<UpdateAffectedGroup> = emptyList(),
    val description: String = ""
)