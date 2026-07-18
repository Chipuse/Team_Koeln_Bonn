package com.example.team_koeln_bonn.domain.use_case.get_barriers

import com.example.team_koeln_bonn.common.Resource
import com.example.team_koeln_bonn.data.remote.dto.toBarrier
import com.example.team_koeln_bonn.data.remote.dto.toBarrierDto
import com.example.team_koeln_bonn.data.repository.BarrierRepositoryImpl
import com.example.team_koeln_bonn.domain.model.Barrier
import com.example.team_koeln_bonn.domain.repository.BarrierRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class UpdateBarrierUseCase(
    private val repository: BarrierRepository = BarrierRepositoryImpl()
) {

    operator fun invoke(barrier: Barrier): Flow<Resource<Barrier>> = flow {
        try {
            emit(Resource.Loading())

            val updatedBarrier =
                repository.updateBarrier(barrier.toBarrierDto())

            emit(Resource.Success(updatedBarrier.toBarrier()))

        } catch (e: IOException) {
            emit(
                Resource.Error(
                    e.localizedMessage
                        ?: "Something went wrong with connecting to the server"
                )
            )
        }
    }
}