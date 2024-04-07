package com.example.my_first_aid_kit.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.my_first_aid_kit.models.Kit
import com.example.my_first_aid_kit.models.Medicament
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.models.MedicationGroup

@Database(
    entities = [Kit::class, Medicament::class, MedicationGroup::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDataBase: RoomDatabase() {
    abstract fun dao(): Dao

    companion object{
        @Volatile
        private var INSTANCE: LocalDataBase? = null

        fun getBataBase(context: Context): LocalDataBase{
            return INSTANCE ?: synchronized(this){
                buildDataBase(context).also{ INSTANCE = it }
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context,
            LocalDataBase::class.java,
            "University_database")
            .fallbackToDestructiveMigration()
            .build()

    }
}