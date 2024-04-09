package com.example.my_first_aid_kit.screen.medicamention

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.my_first_aid_kit.models.Medicament
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.models.MedicationGroup
import com.example.my_first_aid_kit.repository.SettingRepository

class FragmentMedicamentListViewModel(id: Int) : ViewModel() {

    val medList = SettingRepository.getInstance().getMedForKit(id)

    fun addMedicamentGroup(med: MedicamentForKit) {
        if (!checkMed(med)) {
            val medicament = Medicament(barcode = "non", nameMed = med.name, releaseForm = med.releaseForm)
            addMedicament(medicament)
        }

        val getMed = SettingRepository.getInstance().getMedicament(med.name)
        getMed.observeForever(object : Observer<Medicament?> {
            override fun onChanged(value: Medicament?) {
                if (value?.idMed != null) {
                    val newMed = MedicationGroup(idKit = med.idKit, idMed = value.idMed, count = med.count,
                        expirationDate = med.expirationDate, idColor = med.idColor)
                    SettingRepository.getInstance().newMedicamentGroup(newMed)
                    getMed.removeObserver(this)
                }
            }
        })
    }

    private fun addMedicament(medicament: Medicament) {
        SettingRepository.getInstance().newMedicament(medicament)
    }

    private fun checkMed(newMed: MedicamentForKit): Boolean {
        val getMed = SettingRepository.getInstance().getMedicament(newMed.name) ?: return false
        return getMed.value?.nameMed == newMed.name
    }

}