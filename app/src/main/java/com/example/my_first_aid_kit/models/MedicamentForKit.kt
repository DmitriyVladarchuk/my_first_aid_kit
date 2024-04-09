package com.example.my_first_aid_kit.models

import androidx.room.ColumnInfo

data class MedicamentForKit(
    @ColumnInfo(name = "id_med_kit") val idMedKit: Int = 0,
    @ColumnInfo(name = "id_kit") val idKit: Int,
    @ColumnInfo(name = "name_med") val name: String,
    @ColumnInfo(name = "release_form") val releaseForm: String,
    @ColumnInfo(name = "count") var count: Int,
    @ColumnInfo(name = "expiration_date") val expirationDate: String,
    @ColumnInfo(name = "id_color") val idColor: Int
)
