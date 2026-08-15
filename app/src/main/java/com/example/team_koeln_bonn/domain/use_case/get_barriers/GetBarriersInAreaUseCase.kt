package com.example.team_koeln_bonn.domain.use_case.get_barriers

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.team_koeln_bonn.common.Resource
import com.example.team_koeln_bonn.data.remote.dto.BarrierDto
import com.example.team_koeln_bonn.data.remote.dto.toBarrier
import com.example.team_koeln_bonn.data.repository.BarrierRepositoryImpl
import com.example.team_koeln_bonn.domain.model.Barrier
import com.example.team_koeln_bonn.domain.repository.BarrierRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException


// the tutorial uses component injection here to make the refernce to the repository not hard coded
class GetBarriersInAreaUseCase  (
    private val repository: BarrierRepository = BarrierRepositoryImpl()
) {
    operator fun invoke(action: (List<BarrierDto>) -> Unit, centerCoordinates : List<Double>, areaRadius: Double): Flow<Resource<List<Barrier>>> = flow{
        try {
            emit(Resource.Loading())
            val barriers = repository.getBarriersInArea(action, centerCoordinates, areaRadius).map {it.toBarrier()}

            emit(Resource.Success(barriers))
        } /*catch (e : HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected Error happened"))

        }*/ catch (e : IOException){
            emit(Resource.Error(e.localizedMessage ?: "Something went wrong with connecting to the server"))
        }
    }
}