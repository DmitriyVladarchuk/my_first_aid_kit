package com.example.my_first_aid_kit.screen.medicamention.add

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.my_first_aid_kit.R


class SpinnerAdapter(
    context: Context?, textViewResourceId: Int,
    objects: List<String>
) : ArrayAdapter<String?>(context!!, textViewResourceId, objects) {

    var listReleaseForm: List<String> = listOf()
    var idColor: Int = R.color.blue1
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    private fun getCustomView(
        position: Int, parent: ViewGroup?
    ): View {
        val row: View = LayoutInflater.from(parent!!.context)
            .inflate(R.layout.spinner_item, parent, false)

        val label = row.findViewById<TextView>(R.id.tvlReleaseForm)
        label.text = listReleaseForm[position]

        val icon = row.findViewById<ImageView>(R.id.imageReleaseForm)
        when(position){
            0 -> icon.setImageResource(R.drawable.capsule_48px)
            1 -> icon.setImageResource(R.drawable.pill)
            2 -> icon.setImageResource(R.drawable.medication_liquid_48px)
            3 -> icon.setImageResource(R.drawable.vaccines_48px)
            4 -> icon.setImageResource(R.drawable.pediatrics_48px)
        }
        icon.setColorFilter(ContextCompat.getColor(parent.context, idColor))

        return row
    }
}