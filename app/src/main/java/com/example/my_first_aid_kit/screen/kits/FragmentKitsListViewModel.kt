package com.example.my_first_aid_kit.screen.kits

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.my_first_aid_kit.models.Kit
import com.example.my_first_aid_kit.repository.SettingRepository

class FragmentKitsListViewModel : ViewModel() {
    var kitList: LiveData<List<Kit>> = SettingRepository.getInstance().kitList

    fun deleteKit(kit: Kit){
        SettingRepository.getInstance().deleteKit(kit)
    }
}