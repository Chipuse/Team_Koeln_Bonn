package com.example.team_koeln_bonn.data.repository

import com.example.team_koeln_bonn.data.remote.BarrierDatabaseApi
import com.example.team_koeln_bonn.data.remote.dto.BarrierDto
import com.example.team_koeln_bonn.domain.repository.BarrierRepository

class BarrierRepositoryImpl(
    private val api: BarrierDatabaseApi
) : BarrierRepository {
    override suspend fun getBarriers(): List<BarrierDto> {
        return api.getBarriers()
    }
}