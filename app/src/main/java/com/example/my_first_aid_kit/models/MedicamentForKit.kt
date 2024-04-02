package com.example.my_first_aid_kit.models

data class MedicamentForKit(
    val idMedKit: Int,
    val idKit: Int,
    var idMedicament: Int,
    var name: String,
    var idReleaseForm: Int,
    var count: Int,
    var expirationDate: String,
    var idColor: Int
)
