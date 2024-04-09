package com.example.my_first_aid_kit.screen.medicamention.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ViewModelAddMedicamentFactory(private val id: Int): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FragmentAddMedicamentViewModel::class.java)){
            return FragmentAddMedicamentViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}