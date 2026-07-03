package com.example.team_koeln_bonn.data.repository

import com.example.team_koeln_bonn.data.remote.BarrierDatabaseApi
import com.example.team_koeln_bonn.data.remote.FirestoreApiImpl
import com.example.team_koeln_bonn.data.remote.dto.BarrierDto
import com.example.team_koeln_bonn.domain.repository.BarrierRepository

class BarrierRepositoryImpl(
    private val api: BarrierDatabaseApi = FirestoreApiImpl()
) : BarrierRepository {
    override suspend fun getBarriers(action: (List<BarrierDto>) -> Unit): List<BarrierDto> {
        return api.getBarriers(action = action)
    }

    override suspend fun saveBarrier(barrier: BarrierDto): BarrierDto {
        return api.saveBarrier(barrier)
    }
}