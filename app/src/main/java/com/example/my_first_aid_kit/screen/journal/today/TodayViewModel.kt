package com.example.my_first_aid_kit.screen.journal.today

import androidx.lifecycle.ViewModel
import com.example.my_first_aid_kit.models.ReminderDetails
import com.example.my_first_aid_kit.repository.SettingRepository
import com.example.my_first_aid_kit.utill.ValidateData

class TodayViewModel : ViewModel() {
    private var remindersToday: List<ReminderDetails> = listOf()
    val allReminders = SettingRepository.getInstance().getAllReminderWithMedGroup()
    val currentDate = ValidateData.getInstance().getDateToday()

    fun getRemindersToday(): List<ReminderDetails> {
        remindersToday = ValidateData.getInstance().getListToDate(currentDate, allReminders.value!!)
        return remindersToday
    }

}