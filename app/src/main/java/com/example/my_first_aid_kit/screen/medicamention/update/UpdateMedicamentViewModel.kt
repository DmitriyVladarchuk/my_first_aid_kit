package com.example.my_first_aid_kit.screen.medicamention.update

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.my_first_aid_kit.models.Medicament
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.models.MedicationGroup
import com.example.my_first_aid_kit.repository.SettingRepository

class UpdateMedicamentViewModel : ViewModel() {

    var medicament = SettingRepository.getInstance().currentMedForKit

    fun updateMedicament(newMed: MedicamentForKit){

        val editMed = MedicationGroup(medicament.value!!.idMedKit,
            medicament.value!!.idKit,
            medicament.value!!.idMed,
            newMed.count,
            newMed.expirationDate,
            newMed.idColor)

        if(newMed.name != medicament.value!!.name || newMed.releaseForm != medicament.value!!.releaseForm){
            SettingRepository.getInstance().deleteMedicamentGroup(medicament.value!!.idMedKit)
            addMedicamentGroup(newMed)
        }
        else{
            SettingRepository.getInstance().updateMedGroup(editMed)
        }

    }

    private fun addMedicament(medicament: Medicament) {
        SettingRepository.getInstance().newMedicament(medicament)
    }

    private fun addMedicamentGroup(med: MedicamentForKit) {
        val getMed = SettingRepository.getInstance().getMedicamentFromTable(med.name, med.releaseForm)
        getMed.observeForever(object : Observer<Medicament?> {
            override fun onChanged(value: Medicament?) {
                if (value != null) {
                    if (value.nameMed == med.name && value.releaseForm == med.releaseForm) {
                        val newMed = MedicationGroup(idKit = med.idKit, idMed = value.medicamentId, count = med.count,
                            expirationDate = med.expirationDate, idColor = med.idColor)
                        SettingRepository.getInstance().newMedicamentGroup(newMed)
                        getMed.removeObserver(this)
                    } else {
                        val medicament = Medicament(barcode = "non", nameMed = med.name, releaseForm = med.releaseForm)
                        addMedicament(medicament)
                    }
                }
                else{
                    val medicament = Medicament(barcode = "non", nameMed = med.name, releaseForm = med.releaseForm)
                    addMedicament(medicament)
                }
            }
        })
    }

}