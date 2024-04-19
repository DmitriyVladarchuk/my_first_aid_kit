package com.example.my_first_aid_kit.screen.journal.allList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.models.Reminder
import com.example.my_first_aid_kit.models.ReminderDetails
import java.lang.IllegalArgumentException

class AllReminderAdapter(val event: Event) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val VIEW_REMINDER = 1
        const val VIEW_TEXT = 2
    }

    var reminderList: List<ReminderDetails> = listOf()
        set(value) {
            field = value
            if (reminderList.isNotEmpty())
                currentDate = listDate.first().toString()
            notifyDataSetChanged()
        }

    var listDate: List<String> = listOf()
        set(value) {
            field = value
            if (listDate.isNotEmpty())
                currentDate = listDate.first().toString()
        }

    private var currentDate = ""
    private var flag: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReminderViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_reminder, parent, false))

    }

    override fun getItemCount(): Int {
        return (reminderList.size)
    }

    override fun getItemViewType(position: Int): Int {
        if (reminderList[position].reminder.startReminder != currentDate){
            currentDate = reminderList[position].reminder.startReminder
            flag = true
        }
        return VIEW_REMINDER
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ReminderViewHolder -> { holder.bind(reminderList[position]) }
        }
    }

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val card = itemView.findViewById<CardView>(R.id.itemReminder)
        private val test = itemView.findViewById<TextView>(R.id.tvTest)
        private val image = itemView.findViewById<ImageView>(R.id.ivMed)
        private val nameMed = itemView.findViewById<TextView>(R.id.tvNameMed)
        private val timeReminder = itemView.findViewById<TextView>(R.id.tvTimeReminder)
        private val stopReminder = itemView.findViewById<TextView>(R.id.tvDateStop)

        fun bind(reminder: ReminderDetails) {

            if (flag){
                test.visibility = View.VISIBLE
                test.text = currentDate
                flag = false
            }
            nameMed.text = reminder.medicament.nameMed
            timeReminder.text = reminder.reminder.timeInfo
            stopReminder.text = reminder.reminder.stopReminder
            image.setColorFilter(ContextCompat.getColor(itemView.context, reminder.medicationGroup.idColor))
            when(reminder.medicament.releaseForm){
                "Капсулы" -> image.setImageResource(R.drawable.capsule_48px)
                "Таблетки" -> image.setImageResource(R.drawable.pill)
                "Сироп" -> image.setImageResource(R.drawable.medication_liquid_48px)
                "Инъекция" -> image.setImageResource(R.drawable.vaccines_48px)
                "Спрей" -> image.setImageResource(R.drawable.pediatrics_48px)
            }

            card.setOnClickListener{
                event.clickReminder(reminder.reminder)
            }
        }
    }

    interface Event {
        fun clickReminder(reminder: Reminder)
    }

//    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        private val textInfo = itemView.findViewById<TextView>(R.id.tvInfoDate)
//
//        fun bind(){
//            textInfo.text = currentDate
//        }
//    }

}