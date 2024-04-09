package com.example.my_first_aid_kit.screen.medicamention.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentAddMedicamentBinding
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.models.MedicationGroup
import com.example.my_first_aid_kit.screen.medicamention.FragmentMedicamentListArgs
import com.example.my_first_aid_kit.screen.medicamention.FragmentMedicamentListViewModel
import com.example.my_first_aid_kit.screen.medicamention.ViewModelMedicamentFactory


class FragmentAddMedicament : Fragment() {

    private var idColor: Int = R.color.blue1
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var viewModel: FragmentAddMedicamentViewModel
    private var _binding: FragmentAddMedicamentBinding? = null
    val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = ViewModelAddMedicamentFactory(
            FragmentAddMedicamentArgs.fromBundle(requireArguments()).idKit)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FragmentAddMedicamentViewModel::class.java)

        val list = resources.getStringArray(R.array.release_form).toList()
        spinnerAdapter = SpinnerAdapter(requireContext(), R.layout.spinner_item, list)
        spinnerAdapter.listReleaseForm = list

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddMedicamentBinding.inflate(inflater, container, false)
        val view = binding.root

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

        binding.btnAddMed.setOnClickListener {
            val expirationDate = binding.datePicker.dayOfMonth.toString() +
                    "." + binding.datePicker.month.toString() + "." +
                    binding.datePicker.year

            val medGroup = MedicamentForKit(
                idKit = viewModel.idKit,
                name = binding.textFieldMed.text.toString(),
                releaseForm = binding.spinner.selectedItem.toString(),
                count = binding.countMedicament.text.toString().toInt(),
                expirationDate = expirationDate,
                idColor = idColor
            )
            viewModel.addMedicamentGroup(medGroup)
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