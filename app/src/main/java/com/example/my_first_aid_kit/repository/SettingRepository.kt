package com.example.my_first_aid_kit.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData

import com.example.my_first_aid_kit.App

class SettingRepository private constructor(){

    companion object{
        private var INSTANCE: SettingRepository?=null
        const val TAG_DARK_MODE = "dark_mode"
        const val TAG_BACKGROUND_WORK = "background_work"
        const val MODE_DARK = AppCompatDelegate.MODE_NIGHT_YES
        const val MODE_LIGHT = AppCompatDelegate.MODE_NIGHT_NO

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

    fun loadTheme(){
        val context = App.context
        val shared = context.getSharedPreferences(TAG_DARK_MODE,
            AppCompatActivity.MODE_PRIVATE)
        darkMode = shared.getInt(TAG_DARK_MODE, MODE_LIGHT)

        setTheme()
    }

    private fun setTheme(){
        AppCompatDelegate.setDefaultNightMode(darkMode);
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
}