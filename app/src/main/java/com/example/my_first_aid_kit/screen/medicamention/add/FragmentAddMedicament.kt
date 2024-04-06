package com.example.my_first_aid_kit.screen.medicamention.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentAddMedicamentBinding


class FragmentAddMedicament : Fragment() {

    private var idColor: Int = R.color.blue1
    private lateinit var spinnerAdapter: SpinnerAdapter
    private var _binding: FragmentAddMedicamentBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMedicamentBinding.inflate(inflater, container, false)
        val view = binding.root

        val list = resources.getStringArray(R.array.release_form).toList()
        spinnerAdapter = SpinnerAdapter(requireContext(), R.layout.spinner_item, list)
        spinnerAdapter.listReleaseForm = list
        changeColor()
        binding.spinner.adapter = spinnerAdapter

        binding.colorBlue1.setOnClickListener{ idColor = R.color.blue1; changeColor() }
        binding.colorBlue2.setOnClickListener{ idColor = R.color.blue2; changeColor() }
        binding.colorCrimson.setOnClickListener{ idColor = R.color.crimson; changeColor() }
        binding.colorPurple.setOnClickListener{ idColor = R.color.purple; changeColor() }
        binding.colorRed.setOnClickListener{ idColor = R.color.red; changeColor() }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when(position){
                    0 -> binding.ivMed.setImageResource(R.drawable.capsule_48px)
                    1 -> binding.ivMed.setImageResource(R.drawable.pill)
                    2 -> binding.ivMed.setImageResource(R.drawable.medication_liquid_48px)
                    3 -> binding.ivMed.setImageResource(R.drawable.vaccines_48px)
                    4 -> binding.ivMed.setImageResource(R.drawable.pediatrics_48px)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) { }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeColor(){
        binding.ivMed.setColorFilter(ContextCompat.getColor(requireContext(), idColor))
        binding.boxTextInputMed.boxStrokeColor = ContextCompat.getColor(requireContext(), idColor)
        binding.boxTextInputCount.boxStrokeColor = ContextCompat.getColor(requireContext(), idColor)
        binding.btnAddMed.setBackgroundColor(ContextCompat.getColor(requireContext(), idColor))

        spinnerAdapter.idColor = idColor
    }
}