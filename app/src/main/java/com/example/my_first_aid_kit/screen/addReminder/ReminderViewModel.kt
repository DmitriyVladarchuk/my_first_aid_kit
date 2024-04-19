package com.example.my_first_aid_kit.screen.addReminder

import androidx.lifecycle.ViewModel
import com.example.my_first_aid_kit.models.Reminder
import com.example.my_first_aid_kit.repository.SettingRepository

class ReminderViewModel : ViewModel() {

    var idMedKit: Int? = null

    val newReminder = Reminder()

    var time1 = "8:00"
    //var time2 = "15:00"
    //var time3 = "21:00"

    var startReminders = ""

    fun addReminder(){
        SettingRepository.getInstance().newReminder(newReminder)
    }

}