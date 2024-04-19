package com.example.my_first_aid_kit.utill

import com.example.my_first_aid_kit.models.ReminderDetails
import java.time.LocalDate


class ValidateData {

    companion object{
        private var INSTANCE: ValidateData?=null

        fun getInstance(): ValidateData {
            if(INSTANCE == null){
                INSTANCE = ValidateData()
            }
            return INSTANCE ?:
            throw IllegalStateException("DataRepository repository не инициализирован")
        }
    }

    fun collectOnDate(day: Int, mouth: Int, year: Int): String {
        var mouthValid = mouth
        var date = ""
        mouthValid += 1
        date += if (day < 10) {
            "0$day."
        } else {
            "$day."
        }
        date += if (mouthValid < 10) {
            "0$mouthValid."
        } else {
            "$mouthValid."
        }
        date += year
        return date
    }

    fun splitByDate(date: String): MutableMap<String, Int> {
        val dates: MutableMap<String, Int> = mutableMapOf("year" to date.substring(6).toInt(),
            "mouth" to date.substring(3, 5).toInt() - 1,
            "day" to date.substring(0, 2).toInt())
        return dates
    }

    fun getDateToday(): String{
        val today = LocalDate.now()

        val year = today.year
        val month = today.monthValue - 1
        val day = today.dayOfMonth

        return collectOnDate(day, month, year)
    }

    fun getDateTomorrow(): String {
        val tomorrow = LocalDate.now().plusDays(1)

        val year = tomorrow.year
        val month = tomorrow.monthValue - 1
        val day = tomorrow.dayOfMonth

        return collectOnDate(day, month, year)
    }

    fun getListToDate(date: String, list: List<ReminderDetails>): List<ReminderDetails>{
        val newList: MutableList<ReminderDetails> = mutableListOf()
        val currentDate = splitByDate(date)

        for(reminder in list){
            val startDate = splitByDate(reminder.reminder.startReminder)
            val stopDate = splitByDate(reminder.reminder.stopReminder)

            if(currentDate["year"]!! != startDate["year"]!! || currentDate["mouth"]!! != startDate["mouth"]!!)
                continue

            if(currentDate["day"]!! >= startDate["day"]!! && currentDate["day"]!! <= stopDate["day"]!!)
                newList.add(reminder)
        }

        return newList
    }


}