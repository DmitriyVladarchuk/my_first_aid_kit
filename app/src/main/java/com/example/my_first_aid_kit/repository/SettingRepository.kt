package com.example.my_first_aid_kit.repository

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.room.util.convertUUIDToByte

import com.example.my_first_aid_kit.App
import com.example.my_first_aid_kit.DataBase.LocalDataBase
import com.example.my_first_aid_kit.models.Kit
import com.example.my_first_aid_kit.models.Medicament
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.models.MedicationGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SettingRepository private constructor(){

    companion object{
        private var INSTANCE: SettingRepository?=null
        const val TAG_DARK_MODE = "dark_mode"
        const val TAG_BACKGROUND_WORK = "background_work"
        const val MODE_DARK = AppCompatDelegate.MODE_NIGHT_YES
        const val MODE_LIGHT = AppCompatDelegate.MODE_NIGHT_NO
        const val BACKGROUND_WORK_ONE = 12
        const val BACKGROUND_WORK_TWO = 24
        const val BACKGROUND_WORK_TREE = 48
        const val BACKGROUND_WORK_FOUR = 168

        fun getInstance(): SettingRepository{
            if(INSTANCE == null){
                INSTANCE = SettingRepository()
            }
            return INSTANCE ?:
            throw IllegalStateException("DataRepository repository не инициализирован")
        }
    }

    var darkMode: Int = MODE_LIGHT
    var backgroundWork: Int = 12

    private val localDB by lazy {
        LocalDataBase.getBataBase(App.context).dao()
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun onDestroy(){
        coroutineScope.cancel()
    }

    val kitList: LiveData<List<Kit>> = localDB.getAllKits()
        .asLiveData() // конвертируем в тип LiveData

    fun getMedForKit(id: Int): LiveData<List<MedicamentForKit>>{
        return localDB.getMedicamentForKit(id).asLiveData()
    }

    fun getKit(id: Int): LiveData<Kit>{
        return localDB.getKitFromId(id)
    }

    fun newKit(kit: Kit){
        coroutineScope.launch {
            localDB.insertKit(kit)
        }
    }

    fun getMedicament(name: String): LiveData<Medicament>{
        return localDB.getMedicamentFromName(name)
    }

    fun getMedicamentInfo(id: Int): LiveData<Medicament> {
        return localDB.getMedicamentById(id)
    }

    fun newMedicament(medicament: Medicament){
        coroutineScope.launch {
            localDB.insertMedicament(medicament)
        }
    }

    fun newMedicamentGroup(medicationGroup: MedicationGroup){
        coroutineScope.launch{
            localDB.insertMedForKit(medicationGroup)
        }
    }

    fun updateKit(kit: Kit){
        coroutineScope.launch {
            localDB.updateKit(kit)
        }
    }

    fun deleteKit(kit: Kit){
        coroutineScope.launch {
            localDB.deleteKit(kit)
        }
    }


    // Shared Preferences

    fun loadData(){
        loadTheme()
        loadBackgroundWork()
    }

    private fun loadTheme(){
        val context = App.context
        val shared = context.getSharedPreferences(TAG_DARK_MODE,
            AppCompatActivity.MODE_PRIVATE)
        darkMode = shared.getInt(TAG_DARK_MODE, MODE_LIGHT)

        setTheme()
    }

    private fun loadBackgroundWork(){
        val context = App.context
        val shared = context.getSharedPreferences(
            TAG_BACKGROUND_WORK,
            AppCompatActivity.MODE_PRIVATE)
        backgroundWork = shared.getInt(TAG_BACKGROUND_WORK, 12)

//        setBackgroundWork()
    }

    private fun setTheme(){
        AppCompatDelegate.setDefaultNightMode(darkMode);
    }

    private fun setBackgroundWork(){
        TODO()
    }

    fun isDarkMode(flag: Boolean){
        val context = App.context
        val shared = context.getSharedPreferences(TAG_DARK_MODE,
            AppCompatActivity.MODE_PRIVATE)
        val editor = shared!!.edit()
        
        darkMode = if (flag){
            editor!!.putInt(TAG_DARK_MODE, MODE_DARK)
            MODE_DARK
        }
        else{
            editor!!.putInt(TAG_DARK_MODE, MODE_LIGHT)
            MODE_LIGHT
        }
        editor.apply()

        setTheme()
    }

    fun updateBackgroundWork(value: Int){
        val context = App.context
        val shared = context.getSharedPreferences(
            TAG_BACKGROUND_WORK,
            AppCompatActivity.MODE_PRIVATE)
        val editor = shared!!.edit()

        editor!!.putInt(TAG_BACKGROUND_WORK, value)
        backgroundWork = value

        editor.apply()
//        setBackgroundWork()
    }

}