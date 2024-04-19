package com.example.my_first_aid_kit.screen.journal.tomorrow

import androidx.lifecycle.ViewModel
import com.example.my_first_aid_kit.models.ReminderDetails
import com.example.my_first_aid_kit.repository.SettingRepository
import com.example.my_first_aid_kit.utill.ValidateData

class TomorrowViewModel : ViewModel() {
    private var remindersToday: List<ReminderDetails> = listOf()
    val allReminders = SettingRepository.getInstance().getAllReminderWithMedGroup()
    val currentDate = ValidateData.getInstance().getDateTomorrow()

    fun getRemindersToday(): List<ReminderDetails> {
        remindersToday = ValidateData.getInstance().getListToDate(currentDate, allReminders.value!!)
        return remindersToday
    }

}