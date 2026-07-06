package com.example.team_koeln_bonn.data.remote

import android.util.Log
import com.example.team_koeln_bonn.data.remote.dto.BarrierDto
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG
import java.util.UUID

class FirestoreApiImpl(
    private val dataBase: FirebaseFirestore = Firebase.firestore
) : BarrierDatabaseApi {
    override suspend fun getBarriers( action: (List<BarrierDto>) -> Unit ): List<BarrierDto> {
        val barrierList = mutableListOf<BarrierDto>()
        dataBase.collection("barriers")
            .get()
            .addOnSuccessListener { result ->
                for (document in result){

                    barrierList.add(
                        BarrierDto(
                            id = document.get("id") as String,
                            coordinates = document.get("coordinates") as List<Double>,
                            tags = document.get("tags") as List<String>,
                            description = document.get("description") as String
                        )
                    )

                }
                action(barrierList)
            }
        return barrierList
    }

    override suspend fun saveBarrier(barrier: BarrierDto): BarrierDto {
        dataBase.collection("barriers").document(barrier.id.toString()).set(barrier)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        return barrier
    }

    override suspend fun getBarrierById(id: String): BarrierDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBarrier(barrier: BarrierDto): BarrierDto {
        TODO("Not yet implemented")
    }
}