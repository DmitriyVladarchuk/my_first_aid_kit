package com.example.my_first_aid_kit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "medication_group",
    foreignKeys = [ForeignKey(
        entity = Kit::class,
        childColumns = ["id_kit"],
        parentColumns = ["id_kit"],
        onDelete = CASCADE
    ),
    ForeignKey(
        entity = Medicament::class,
        childColumns = ["id_med"],
        parentColumns = ["id_med"],
        onDelete = CASCADE
    )])
data class MedicationGroup(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_med_kit")
    var idMedKit: Int,

    @ColumnInfo(name = "id_kit")
    var idKit: Int,

    @ColumnInfo(name = "id_med")
    var idMed: Int,
    var count: Int,

    @ColumnInfo(name = "expiration_date")
    var expirationDate: String,

    @ColumnInfo(name = "id_color")
    var idColor: Int
)
