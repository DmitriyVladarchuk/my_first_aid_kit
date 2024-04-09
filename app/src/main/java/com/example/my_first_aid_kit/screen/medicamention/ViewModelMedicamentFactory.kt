package com.example.my_first_aid_kit.screen.medicamention

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ViewModelMedicamentFactory(private val id: Int): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FragmentMedicamentListViewModel::class.java)){
            return FragmentMedicamentListViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}