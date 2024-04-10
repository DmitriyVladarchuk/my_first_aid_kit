package com.example.my_first_aid_kit.utill

import androidx.appcompat.app.AppCompatDelegate
import com.example.my_first_aid_kit.repository.SettingRepository


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
        //val dates = ArrayList<Int>()
        val dates: MutableMap<String, Int> = mutableMapOf("year" to date.substring(6).toInt(),
            "mouth" to date.substring(3, 5).toInt() - 1,
            "day" to date.substring(0, 2).toInt())
//        dates.add(date.substring(6).toInt())
//        // Для коректной записи в DatePicker
//        dates.add(date.substring(3, 5).toInt() - 1)
//        dates.add(date.substring(0, 2).toInt())
        return dates
    }

}