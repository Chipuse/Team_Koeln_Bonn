package com.example.team_koeln_bonn.domain.repository

import com.example.team_koeln_bonn.data.remote.dto.BarrierDto

interface BarrierRepository {

    suspend fun getBarriers(
        action: (List<BarrierDto>) -> Unit
    ): List<BarrierDto>

    suspend fun getBarriersInArea(
        action: (List<BarrierDto>) -> Unit,
        centerCoordinates : List<Double>,
        areaRadius : Double = 0.05
    ): List<BarrierDto>

    //ToDo suspend fun "fetch image of barrier"(barrierId: String)...

    suspend fun saveBarrier(barrier: BarrierDto): BarrierDto //speichert eine neue Barriere

    suspend fun updateBarrier(barrier: BarrierDto): BarrierDto //aktualisert eine bestehende Barriere

    suspend fun deleteBarrier(id: String) //löscht eine barriere anhand ihrer ID
}