package com.example.my_first_aid_kit.screen.kits.add

import androidx.lifecycle.ViewModel
import com.example.my_first_aid_kit.models.Kit
import com.example.my_first_aid_kit.repository.SettingRepository

class FragmentAddKitViewModel : ViewModel() {

    fun addKit(name: String, idColor: Int){
        val kit = Kit(name = name, idColor = idColor)
        SettingRepository.getInstance().newKit(kit)
    }
}