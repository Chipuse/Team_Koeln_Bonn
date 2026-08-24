package com.example.team_koeln_bonn.notification

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class BarrierFirestoreListener(
    context: Context
) {

    private val notificationManager =
        BarrierNotificationManager(context)

    private var listenerRegistration: ListenerRegistration? = null
    private var firstSnapshot = true

    fun startListening() {

        listenerRegistration = FirebaseFirestore.getInstance()
            .collection("barriers")
            .addSnapshotListener { snapshots, error ->

                if (error != null) {
                    Log.e(
                        "BARRIER_LISTENER",
                        "Fehler beim Firestore Listener",
                        error
                    )
                    return@addSnapshotListener
                }

                if (snapshots == null) {
                    return@addSnapshotListener
                }

                // Beim Start bestehende Barrieren nicht melden
                if (firstSnapshot) {
                    firstSnapshot = false
                    return@addSnapshotListener
                }

                snapshots.documentChanges.forEach { change ->

                    when (change.type) {

                        DocumentChange.Type.ADDED -> {
                            notificationManager.showNotification(
                                "Neue Barriere",
                                "Eine neue Barriere wurde erstellt."
                            )
                        }

                        DocumentChange.Type.MODIFIED -> {
                            notificationManager.showNotification(
                                "Barriere aktualisiert",
                                "Eine Barriere wurde geändert."
                            )
                        }

                        DocumentChange.Type.REMOVED -> {
                            notificationManager.showNotification(
                                "Barriere gelöscht",
                                "Eine Barriere wurde entfernt."
                            )
                        }
                    }
                }
            }
    }

    fun stopListening() {
        listenerRegistration?.remove()
        listenerRegistration = null
    }
}