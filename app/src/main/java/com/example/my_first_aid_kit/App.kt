package com.example.my_first_aid_kit

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.my_first_aid_kit.repository.SettingRepository

class App : Application() {
    override fun onCreate(){
        super.onCreate()
        SettingRepository.getInstance().loadTheme()
    }

    init{
        instance = this
    }

    companion object{
        private var instance:App? = null

        val context
            get() = applicationContext()

        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}