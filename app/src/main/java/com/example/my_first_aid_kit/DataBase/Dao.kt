package com.example.my_first_aid_kit.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.my_first_aid_kit.models.Kit
import com.example.my_first_aid_kit.models.Medicament
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.models.MedicationGroup
import com.example.my_first_aid_kit.models.Reminder
import com.example.my_first_aid_kit.models.ReminderDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM kits")
    fun getAllKits(): Flow<List<Kit>>

    @Query("SELECT * FROM kits WHERE id_kit=:id")
    fun getKitFromId(id: Int): LiveData<Kit>

    @Insert(entity = Kit::class)
    suspend fun insertKit(kit: Kit)

    @Update(entity = Kit::class)
    suspend fun updateKit(kit: Kit)

    @Delete(entity = Kit::class)
    suspend fun deleteKit(kit: Kit)
    // ...

    @Query("SELECT * FROM medicament")
    fun getAllMedicament(): Flow<List<Medicament>>

    @Query("SELECT * FROM medicament WHERE name_med=:name")
    fun getMedicamentFromName(name: String): LiveData<Medicament>

    @Query("SELECT * FROM medicament WHERE name_med=:name and release_form=:releaseForm")
    fun isMedicamentFromTable(name: String, releaseForm: String): LiveData<Medicament>

    @Query("SELECT * FROM medicament WHERE id_med=:id")
    fun getMedicamentById(id: Int): LiveData<Medicament>

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

    @Query("DELETE FROM medication_group WHERE id_med_kit=:id")
    suspend fun deleteMedGroupById(id: Int)

    @Query("SELECT mg.id_med_kit, mg.id_kit, m.id_med, m.name_med, m.release_form, mg.count, mg.expiration_date, mg.id_color FROM medication_group AS mg INNER JOIN medicament AS m ON mg.id_med = m.id_med WHERE mg.id_kit = :idKit")
    fun getMedicamentForKit(idKit: Int): Flow<List<MedicamentForKit>>

    @Query("SELECT mg.id_med_kit, mg.id_kit, m.id_med, m.name_med, m.release_form, mg.count, mg.expiration_date, mg.id_color FROM medication_group AS mg INNER JOIN medicament AS m ON mg.id_med = m.id_med WHERE mg.id_med_kit = :id")
    fun getMedicamentForKitById(id: Int): LiveData<MedicamentForKit>

    // Reminders
    @Query("SELECT * FROM reminders")
    fun getAllReminders(): Flow<List<Reminder>>

    @Query("SELECT * FROM reminders WHERE id_med_kit=:idMedKit")
    fun getAllRemindersByIdMedKit(idMedKit: Int): Flow<List<Reminder>>

    @Query("SELECT * FROM reminders WHERE id_reminder=:idReminder")
    fun getReminderById(idReminder: Int): LiveData<Reminder>

    @Insert(entity = Reminder::class)
    suspend fun insertReminder(reminder: Reminder)

    @Update(entity = Reminder::class)
    suspend fun updateReminder(reminder: Reminder)

    @Delete(entity = Reminder::class)
    suspend fun deleteReminder(reminder: Reminder)

//    @Query("SELECT * FROM reminders INNER JOIN medication_group ON reminders.id_med_kit = medication_group.id_med_kit")
//    fun getAllReminderWithMedicationGroup(): LiveData<List<ReminderWithMedicationGroup>>

    @Transaction
    @Query("SELECT * FROM reminders INNER JOIN medication_group ON reminders.id_med_kit = medication_group.id_med_kit INNER JOIN medicament ON medication_group.id_med = medicament.id_med")
    fun getAllReminderWithMedicationGroup(): Flow<List<ReminderDetails>>
}