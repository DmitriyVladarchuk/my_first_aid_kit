package com.example.my_first_aid_kit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kits")
data class Kit(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_kit")
    var idKit: Int = 0,
    @ColumnInfo(name = "name_kit")
    var name: String,
    @ColumnInfo(name = "id_color")
    var idColor: Int
)
