package com.example.team_koeln_bonn.data.remote

import com.example.team_koeln_bonn.data.remote.dto.BarrierDto

//ToDo handling calls to our future server holding our barrier infos
interface BarrierDatabaseApi {

    suspend fun getBarriers(action: (List<BarrierDto>) -> Unit ) : List<BarrierDto>
    //Firebase researchen

    suspend fun saveBarrier(barrier : BarrierDto) : BarrierDto //speichert neue barriere in Firebase
    suspend fun getBarrierById(id : String) : BarrierDto //lädt eine barriere anhand ihrer id
    suspend fun updateBarrier(barrier: BarrierDto) : BarrierDto //aktualisiert eine bestehende Barriere in Firebase
    suspend fun deleteBarrier(id: String) //löscht Barriere aus firestore
}