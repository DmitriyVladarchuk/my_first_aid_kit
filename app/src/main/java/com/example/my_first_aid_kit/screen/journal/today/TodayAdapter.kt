package com.example.my_first_aid_kit.screen.journal.today

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
import com.example.my_first_aid_kit.screen.medicamention.adapter.RVMedicamentAdapter

class TodayAdapter: RecyclerView.Adapter<TodayAdapter.ReminderItem>() {

    var remindersToday: List<ReminderDetails> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderItem {
        return ReminderItem(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false))
    }

    override fun onBindViewHolder(holder: ReminderItem, position: Int) {
        holder.bind(remindersToday[holder.adapterPosition])
    }

    override fun getItemCount(): Int = remindersToday.size

    inner class ReminderItem(view: View) : RecyclerView.ViewHolder(view){

        private val image = itemView.findViewById<ImageView>(R.id.ivMed)
        private val nameMed = itemView.findViewById<TextView>(R.id.tvNameMed)
        private val timeReminder = itemView.findViewById<TextView>(R.id.tvTimeReminder)
        private val stopReminder = itemView.findViewById<TextView>(R.id.tvDateStop)

        fun bind(reminder: ReminderDetails) {
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
        }

    }
}