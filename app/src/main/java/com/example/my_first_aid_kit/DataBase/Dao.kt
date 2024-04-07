package com.example.my_first_aid_kit.DataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.my_first_aid_kit.models.Kit
import com.example.my_first_aid_kit.models.Medicament
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.models.MedicationGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM kits")
    fun getAllKits(): Flow<List<Kit>>

    @Insert(entity = Kit::class)
    suspend fun insertKit(kit: Kit)

    @Update(entity = Kit::class)
    suspend fun updateKit(kit: Kit)

    @Delete(entity = Kit::class)
    suspend fun deleteKit(kit: Kit)
    // ...

    @Query("SELECT * FROM medicament")
    fun getAllMedicament(): Flow<List<Medicament>>

    @Insert(entity = Medicament::class)
    suspend fun insertMedicament(medicament: Medicament)

    @Update(entity = Medicament::class)
    suspend fun updateMedicament(medicament: Medicament)

    @Delete(entity = Medicament::class)
    suspend fun deleteMedicament(medicament: Medicament)
    // ...

    @Query("SELECT * FROM medication_group WHERE id_kit=:id")
    fun getAllMedForKit(id: Int): Flow<List<MedicationGroup>>

    @Insert(entity = MedicationGroup::class)
    suspend fun insertMedForKit(medicationGroup: MedicationGroup)

    @Update(entity = MedicationGroup::class)
    suspend fun updateMedForKit(medicationGroup: MedicationGroup)

    @Delete(entity = MedicationGroup::class)
    suspend fun deleteMedForKit(medicationGroup: MedicationGroup)
    // ...
}