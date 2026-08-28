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

    // Maximal eine Notification pro Stunde
    private var lastNotificationTime = 0L

    private val notificationCooldown =
        60 * 60 * 1000L // 1 Stunde

    companion object {

        // Merkt sich Barrieren, die auf diesem Gerät gelöscht wurden
        private val locallyDeletedBarrierIds =
            mutableSetOf<String>()

        fun markAsLocallyDeleted(barrierId: String) {

            locallyDeletedBarrierIds.add(barrierId)

            Log.d(
                "BARRIER_LISTENER",
                "Lokale Löschung vorgemerkt: $barrierId"
            )
        }
    }

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

                // Beim ersten Laden keine Notifications
                // für bereits vorhandene Barrieren anzeigen
                if (firstSnapshot) {
                    firstSnapshot = false
                    return@addSnapshotListener
                }

                snapshots.documentChanges.forEach { change ->

                    // Eigene lokale Änderung auf diesem Gerät
                    // -> keine Notification anzeigen
                    if (change.document.metadata.hasPendingWrites()) {
                        Log.d(
                            "BARRIER_LISTENER",
                            "Eigene Änderung erkannt - keine Notification"
                        )
                        return@forEach
                    }

                    val rawDescription =
                        change.document.getString("description")
                            ?: "Keine Beschreibung"

                    // Alles nach ":" ausblenden
                    // Beispiel:
                    // "NY Centrum 3: 40.77 Lon und -73.97 Lat"
                    // wird zu:
                    // "NY Centrum 3"
                    val cleanDescription =
                        rawDescription.substringBefore(":").trim()

                    val tags =
                        change.document.get("tags") as? List<*>

                    val tagText =
                        tags
                            ?.mapNotNull { tag ->
                                translateTag(tag?.toString())
                            }
                            ?.joinToString(", ")
                            ?.takeIf { it.isNotBlank() }
                            ?: "Keine Kategorie"

                    // Beschreibung kürzen, damit die Notification kompakter bleibt
                    val shortDescription =
                        cleanDescription.take(40)

                    val body =
                        "$shortDescription · $tagText"

                    when (change.type) {

                        DocumentChange.Type.ADDED -> {

                            if (!canShowNotification()) {
                                return@forEach
                            }

                            Log.d(
                                "BARRIER_LISTENER",
                                "Neue Barriere erkannt: $cleanDescription"
                            )

                            notificationManager.showNotification(
                                title = "Neue Barriere",
                                body = body
                            )
                        }

                        DocumentChange.Type.MODIFIED -> {

                            if (!canShowNotification()) {
                                return@forEach
                            }

                            Log.d(
                                "BARRIER_LISTENER",
                                "Barriere aktualisiert: $cleanDescription"
                            )

                            notificationManager.showNotification(
                                title = "Barriere aktualisiert",
                                body = body
                            )
                        }

                        DocumentChange.Type.REMOVED -> {

                            val barrierId =
                                change.document.id

                            // Prüfen, ob diese Barriere
                            // auf diesem Gerät gelöscht wurde
                            if (
                                locallyDeletedBarrierIds.remove(
                                    barrierId
                                )
                            ) {

                                Log.d(
                                    "BARRIER_LISTENER",
                                    "Eigene Löschung erkannt - keine Notification"
                                )

                                return@forEach
                            }

                            if (!canShowNotification()) {
                                return@forEach
                            }

                            Log.d(
                                "BARRIER_LISTENER",
                                "Barriere gelöscht: $cleanDescription"
                            )

                            notificationManager.showNotification(
                                title = "Barriere gelöscht",
                                body = body
                            )
                        }
                    }
                }
            }
    }

    private fun canShowNotification(): Boolean {

        val now = System.currentTimeMillis()

        if (now - lastNotificationTime < notificationCooldown) {

            Log.d(
                "BARRIER_LISTENER",
                "Notification übersprungen - Stundenlimit"
            )

            return false
        }

        lastNotificationTime = now

        return true
    }

    private fun translateTag(tag: String?): String? {

        return when (tag) {
            "WALKING" -> "Gehen"
            "SEEING" -> "Sehen"
            "HEARING" -> "Hören"
            "OTHER" -> "Sonstiges"
            null -> null
            else -> tag
        }
    }

    fun stopListening() {

        listenerRegistration?.remove()

        listenerRegistration = null
    }
}