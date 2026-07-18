package com.example.team_koeln_bonn.data.repository

import com.example.team_koeln_bonn.data.remote.BarrierDatabaseApi
import com.example.team_koeln_bonn.data.remote.FirestoreApiImpl
import com.example.team_koeln_bonn.data.remote.dto.BarrierDto
import com.example.team_koeln_bonn.domain.repository.BarrierRepository

class BarrierRepositoryImpl(
    private val api: BarrierDatabaseApi = FirestoreApiImpl()
) : BarrierRepository {

    override suspend fun getBarriers(
        action: (List<BarrierDto>) -> Unit
    ): List<BarrierDto> {
        return api.getBarriers(action = action)
    }

    override suspend fun saveBarrier(barrier: BarrierDto): BarrierDto {
        return api.saveBarrier(barrier)
    }

    // Aktualisiert eine bestehende Barriere in Firestore
    override suspend fun updateBarrier(barrier: BarrierDto): BarrierDto {
        return api.updateBarrier(barrier)
    }

    // Löscht eine Barriere anhand ihrer ID aus Firestore
    override suspend fun deleteBarrier(id: String) {
        api.deleteBarrier(id)
    }
}