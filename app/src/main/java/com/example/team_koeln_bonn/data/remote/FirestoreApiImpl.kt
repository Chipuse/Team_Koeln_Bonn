package com.example.team_koeln_bonn.data.remote

import android.util.Log
import com.example.team_koeln_bonn.data.remote.dto.BarrierDto
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG

class FirestoreApiImpl(
    private val dataBase: FirebaseFirestore = Firebase.firestore
) : BarrierDatabaseApi {

    override suspend fun getBarriers(
        action: (List<BarrierDto>) -> Unit
    ): List<BarrierDto> {
        val barrierList = mutableListOf<BarrierDto>()

        dataBase.collection("barriers")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    barrierList.add(
                        BarrierDto(
                            id = document.get("id") as String,
                            lon = document.get("lon") as  Double,
                            lat = document.get("lat") as Double,
                            tags = document.get("tags") as List<String>,
                            description = document.get("description") as String
                        )
                    )
                }

                action(barrierList)
            }

        return barrierList
    }

    override suspend fun getBarriersInArea(
        action: (List<BarrierDto>) -> Unit,
        centerCoordinates: List<Double>
    ): List<BarrierDto> {
        if(centerCoordinates == null || centerCoordinates.size < 2){
            //fallback if coordinates are not applicable: Load All barriers function instead
            return getBarriers(action)
        }

        val barrierList = mutableListOf<BarrierDto>()

        dataBase.collection("barriers")
            //.where() conditions for long and lap
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    barrierList.add(
                        BarrierDto(
                            id = document.get("id") as String,
                            lon = document.get("lon") as  Double,
                            lat = document.get("lat") as Double,
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
        dataBase.collection("barriers")
            .document(barrier.id.toString())
            .set(barrier)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error writing document", e)
            }

        return barrier
    }

    override suspend fun getBarrierById(id: String): BarrierDto {
        TODO("Not yet implemented")
    }

    // Aktualisiert eine bestehende Barriere in Firestore
    override suspend fun updateBarrier(barrier: BarrierDto): BarrierDto {
        dataBase.collection("barriers")
            .document(barrier.id.toString())
            .set(barrier)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }

        return barrier
    }

    //Löscht eine Barriere anhand ihrer ID aus Firestore
    override suspend fun deleteBarrier(id: String) {
        dataBase.collection("barriers")
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
            }
    }
}