package com.example.my_first_aid_kit.screen.setting

import androidx.lifecycle.ViewModel
import com.example.my_first_aid_kit.repository.SettingRepository

class FragmentSettingViewModel : ViewModel() {
    var darkMode = SettingRepository.getInstance().darkMode
    var backgroundWork = SettingRepository.getInstance().backgroundWork

    fun changeDarkMode(){
        SettingRepository.getInstance().isDarkMode(!setSwitchDarkMode())
        darkMode = SettingRepository.getInstance().darkMode
    }

    fun changeBackgroundWork(value: Int){
        SettingRepository.getInstance().updateBackgroundWork(value)
        backgroundWork = SettingRepository.getInstance().backgroundWork
    }

    fun setSwitchDarkMode(): Boolean = darkMode == SettingRepository.MODE_DARK

}