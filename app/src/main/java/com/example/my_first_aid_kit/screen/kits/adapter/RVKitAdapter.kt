package com.example.my_first_aid_kit.screen.kits.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.models.Kit
import java.lang.IllegalArgumentException

class RVKitAdapter(val eventKit: EventKit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        const val VIEW_KIT = 1
        const val VIEW_ADD_KIT = 2
    }

    var kitList: List<Kit> = listOf<Kit>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_ADD_KIT ->{
                AddKitViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_kit_add, parent, false))
            }
            VIEW_KIT -> {
                KitViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_kit, parent, false))
            }
            else -> { throw IllegalArgumentException("Ошибка отображения элемента RVKIT") }
        }
    }

    override fun getItemCount(): Int = kitList.size + 1

    override fun getItemViewType(position: Int): Int {
        return when{
            position == kitList.size -> VIEW_ADD_KIT
            else -> VIEW_KIT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is KitViewHolder -> { holder.bind(kitList[position], holder.adapterPosition) }
            is AddKitViewHolder -> { holder.bind() }
        }
    }

    inner class KitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardKit = itemView.findViewById<CardView>(R.id.cart_kit)
        private val imageColor = itemView.findViewById<ImageView>(R.id.image_kit)
        private val nameKit = itemView.findViewById<TextView>(R.id.nameKit)

        fun bind(kit: Kit, position: Int){
            imageColor.setColorFilter(ContextCompat.getColor(itemView.context, kit.idColor))
            nameKit.text = kit.name
            cardKit.setOnClickListener{ eventKit.clickKit(position) }
            cardKit.setOnLongClickListener{
                eventKit.longClickKit(kit)
                true
            }
        }
    }

    inner class AddKitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardAddKit = itemView.findViewById<CardView>(R.id.cart_kit_add)

        fun bind(){
            cardAddKit.setOnClickListener{ eventKit.clickAddKit() }
        }
    }

    interface EventKit{
        fun clickKit(position: Int)
        fun clickAddKit()
        fun longClickKit(kit: Kit)
    }
}