package com.example.my_first_aid_kit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicament")
data class Medicament(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_med")
    var idMed: Int,

    var barcode: String,

    @ColumnInfo(name = "name_med")
    var nameMed: String,

    @ColumnInfo(name = "release_form")
    var releaseForm: String,
)
