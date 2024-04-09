package com.example.my_first_aid_kit.screen.medicamention.adapter

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

class RVMedicamentAdapter(val eventMed: EventMedicament) : RecyclerView.Adapter<RVMedicamentAdapter.MedicamentItem>() {

    var medicamentList: List<MedicamentForKit> = listOf<MedicamentForKit>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentItem {
        return MedicamentItem(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicament, parent, false))
    }

    override fun getItemCount(): Int = medicamentList.size

    override fun onBindViewHolder(holder: MedicamentItem, position: Int) {
        holder.bind(medicamentList[holder.adapterPosition])
    }

    inner class MedicamentItem(view: View) : RecyclerView.ViewHolder(view){
        private var card = view.findViewById<CardView>(R.id.medicamentItemView)
        private var image = view.findViewById<ImageView>(R.id.image_medicament)
        private var name = view.findViewById<TextView>(R.id.nameMedicament)
        private var expirationDate = view.findViewById<TextView>(R.id.expirationDate)
        private var count = view.findViewById<TextView>(R.id.totalMedicament)

        fun bind(medicament: MedicamentForKit){
            image.setColorFilter(ContextCompat.getColor(itemView.context, medicament.idColor))
            when(medicament.releaseForm){
                "Капсулы" -> image.setImageResource(R.drawable.capsule_48px)
                "Таблетки" -> image.setImageResource(R.drawable.pill)
                "Сироп" -> image.setImageResource(R.drawable.medication_liquid_48px)
                "Инъекция" -> image.setImageResource(R.drawable.vaccines_48px)
                "Спрей" -> image.setImageResource(R.drawable.pediatrics_48px)
            }
            name.text = medicament.name
            expirationDate.text = medicament.expirationDate
            count.text = medicament.count.toString()

            card.setOnClickListener{ eventMed.clickMedicament(medicament) }
        }
    }

    interface EventMedicament{
        fun clickMedicament(medicament: MedicamentForKit)
    }
}