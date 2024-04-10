package com.example.my_first_aid_kit.screen.medicamention.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentAddMedicamentBinding
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.screen.medicamention.add.SpinnerAdapter
import com.example.my_first_aid_kit.utill.ValidateData

class FragmentUpdateMedicament : Fragment() {

    private var idColor: Int = R.color.blue1
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var viewModel: UpdateMedicamentViewModel
    private var _binding: FragmentAddMedicamentBinding? = null
    val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(UpdateMedicamentViewModel::class.java)
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

        binding.tvInfo.text = getText(R.string.update)
        binding.btnAddMed.text = getText(R.string.update)

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

        viewModel.medicament.observe(viewLifecycleOwner){
            binding.textFieldMed.setText(viewModel.medicament.value?.name ?: "")


            val list = resources.getStringArray(R.array.release_form).toList()
            for(i in list.indices){
                if(list[i] == viewModel.medicament.value!!.releaseForm){
                    binding.spinner.setSelection(i)
                }
            }

            val date = ValidateData.getInstance().splitByDate(viewModel.medicament.value?.expirationDate ?: "01.02.2022")
            binding.datePicker.updateDate(date["year"]!!, date["mouth"]!!, date["day"]!!)
            binding.countMedicament.setText(viewModel.medicament.value!!.count.toString())
            idColor = viewModel.medicament.value!!.idColor

            changeColor()
        }

        binding.btnAddMed.setOnClickListener {

            val newMed = MedicamentForKit(viewModel.medicament.value!!.idMedKit,
                viewModel.medicament.value!!.idKit,
                viewModel.medicament.value!!.idMed,
                binding.textFieldMed.text.toString(),
                binding.spinner.selectedItem.toString(),
                binding.countMedicament.text.toString().toInt(),
                ValidateData.getInstance().collectOnDate(binding.datePicker.dayOfMonth,
                    binding.datePicker.month,
                    binding.datePicker.year),
                idColor
            )

            viewModel.updateMedicament(newMed)

            view.findNavController().popBackStack()
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
        //spinnerAdapter.idColor = idColor
    }
}