package com.example.team_koeln_bonn.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.team_koeln_bonn.model.UpdateAffectedGroup

class BarrierUpdateViewModel : ViewModel() {

    // Ausgewählte betroffene Personengruppen
    var selectedGroups by mutableStateOf(setOf<UpdateAffectedGroup>())
        private set

    // Beschreibung der Barriere
    var description by mutableStateOf("")
        private set

    // Fügt eine Gruppe hinzu oder entfernt sie wieder
    fun toggleGroup(group: UpdateAffectedGroup) {
        selectedGroups =
            if (group in selectedGroups) {
                selectedGroups - group
            } else {
                selectedGroups + group
            }
    }

    // Aktualisiert die Beschreibung
    fun updateDescription(value: String) {
        description = value
    }
}