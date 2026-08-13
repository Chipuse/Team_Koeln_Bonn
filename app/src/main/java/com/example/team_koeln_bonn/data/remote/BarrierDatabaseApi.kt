package com.example.team_koeln_bonn.data.remote

import com.example.team_koeln_bonn.data.remote.dto.BarrierDto

//ToDo handling calls to our future server holding our barrier infos
interface BarrierDatabaseApi {

    suspend fun getBarriers(action: (List<BarrierDto>) -> Unit ) : List<BarrierDto>

    suspend fun getBarriersInArea( action: (List<BarrierDto>) -> Unit, centerCoordinates : List<Double>) : List<BarrierDto>

    suspend fun saveBarrier(barrier : BarrierDto) : BarrierDto
    suspend fun getBarrierById(id : String) : BarrierDto
    suspend fun updateBarrier(barrier: BarrierDto) : BarrierDto
    suspend fun deleteBarrier(id: String)
}