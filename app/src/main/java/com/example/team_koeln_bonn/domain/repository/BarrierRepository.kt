package com.example.team_koeln_bonn.domain.repository

import com.example.team_koeln_bonn.data.remote.dto.BarrierDto

interface BarrierRepository {
    suspend fun getBarriers(): List<BarrierDto>
    //ToDo suspend fun "fetch image of barrier"(barrierId: String)...
}