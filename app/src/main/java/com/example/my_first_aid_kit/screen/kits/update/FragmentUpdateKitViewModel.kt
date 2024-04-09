package com.example.my_first_aid_kit.screen.kits.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.my_first_aid_kit.models.Kit
import com.example.my_first_aid_kit.repository.SettingRepository

class FragmentUpdateKitViewModel(id: Int) : ViewModel() {

    val currentKit: LiveData<Kit> = SettingRepository.getInstance().getKit(id)

    fun updateKit(name: String, idColor: Int){
        val kit = Kit(currentKit.value!!.idKit, name, idColor)
        SettingRepository.getInstance().updateKit(kit)
    }
}