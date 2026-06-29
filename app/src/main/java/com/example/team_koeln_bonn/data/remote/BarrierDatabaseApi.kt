package com.example.team_koeln_bonn.data.remote

import com.example.team_koeln_bonn.data.remote.dto.BarrierDto

//ToDo handling calls to our future server holding our barrier infos
interface BarrierDatabaseApi {

    suspend fun getBarriers() : List<BarrierDto>
    //Firebase researchen
}

class BarrierDatabaseApiMockup : BarrierDatabaseApi{
    override suspend fun getBarriers(): List<BarrierDto> {
        return emptyList()
    }
}