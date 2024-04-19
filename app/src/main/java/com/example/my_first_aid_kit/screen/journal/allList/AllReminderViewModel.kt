package com.example.my_first_aid_kit.screen.journal.allList

import androidx.lifecycle.ViewModel
import com.example.my_first_aid_kit.models.Reminder
import com.example.my_first_aid_kit.repository.SettingRepository

class AllReminderViewModel : ViewModel() {

    val allReminder = SettingRepository.getInstance().getAllReminderWithMedGroup()

    var listAllDate : List<String> = listOf()

    fun initListDate() {
        val newList : MutableList<String> = mutableListOf()
        for (i in 0..< allReminder.value!!.size){
            val date = allReminder.value!![i].reminder.startReminder
            if (allReminder.value!![i].reminder.startReminder !in newList){
                newList.add(date)
            }
        }
        listAllDate = newList
    }

    fun deleteReminder(reminder: Reminder) {
        SettingRepository.getInstance().deleteReminder(reminder)
    }

}