package com.example.my_first_aid_kit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "reminders",
    foreignKeys = [ForeignKey(
        entity = MedicationGroup::class,
        childColumns = ["id_med_kit"],
        parentColumns = ["id_med_kit"],
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = Medicament::class,
            childColumns = ["id_med"],
            parentColumns = ["id_med"],
            onDelete = ForeignKey.CASCADE
        )])
data class Reminder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_reminder")
    var idReminder: Int = 0,

    @ColumnInfo(name = "id_med_kit")
    var idMedKit: Int = 0,

    @ColumnInfo(name = "id_med")
    var idMed: Int = 0,

    @ColumnInfo(name = "start_reminder")
    var startReminder: String = "",

    @ColumnInfo(name = "stop_reminder")
    var stopReminder: String = "",

    @ColumnInfo(name = "time_info")
    var timeInfo: String = ""
)
