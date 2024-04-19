package com.example.my_first_aid_kit.models

import androidx.room.Embedded
import androidx.room.Relation

data class ReminderWithMedicationDetails(
    @Embedded
    val reminder: Reminder,
    @Relation(
        parentColumn = "id_med_kit",
        entityColumn = "id_med_kit"
    )
    val medicationGroup: MedicationGroup,
    @Relation(
        parentColumn = "id_med",
        entityColumn = "id_med"
    )
    val medicament: Medicament
)
