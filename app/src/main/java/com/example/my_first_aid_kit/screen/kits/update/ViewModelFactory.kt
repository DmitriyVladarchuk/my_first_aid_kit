package com.example.my_first_aid_kit.screen.kits.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.my_first_aid_kit.screen.kits.add.FragmentAddKitViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val id: Int): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FragmentUpdateKitViewModel::class.java)){
            return FragmentUpdateKitViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}