package com.example.my_first_aid_kit.screen.journal

import androidx.lifecycle.ViewModel
import com.example.my_first_aid_kit.repository.SettingRepository

class JournalListViewModel : ViewModel() {

    val allReminder = SettingRepository.getInstance().getAllReminderWithMedGroup()
    val reminderToday = ""
    val reminderTomorrow = ""

}