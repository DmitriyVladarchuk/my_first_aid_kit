package com.example.my_first_aid_kit.screen.medicamention

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentMedicamentionListBinding
import com.example.my_first_aid_kit.models.Kit
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.models.MedicationGroup
import com.example.my_first_aid_kit.repository.SettingRepository
import com.example.my_first_aid_kit.screen.medicamention.adapter.RVMedicamentAdapter
import com.google.android.material.textfield.TextInputEditText

class FragmentMedicamentList : Fragment(), RVMedicamentAdapter.EventMedicament {

    private lateinit var viewModel: MedGroupListViewModel
    private var _binding: FragmentMedicamentionListBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicamentionListBinding.inflate(inflater, container, false)
        val view = binding.root

        val idKit = FragmentMedicamentListArgs.fromBundle(requireArguments()).idKit
        val viewModelFactory = ViewModelMedicamentFactory(idKit)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MedGroupListViewModel::class.java)

        viewModel.kit.observe(viewLifecycleOwner){
            binding.textView5.text = it.name
        }

        val adapter = RVMedicamentAdapter(this)
        binding.rvMedicament.adapter = adapter
        viewModel.medList.observe(viewLifecycleOwner){
            adapter.medicamentList = it
        }

        binding.rvMedicament.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.addMedicamentButton.setOnClickListener{
            val action = FragmentMedicamentListDirections
                .actionFragmentMedicamentListToFragmentAddMedicament(idKit)
            view.findNavController().navigate(action)
        }

        binding.btnBackForKit.setOnClickListener {
            view.findNavController().popBackStack()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickMedicament(medicament: MedicamentForKit) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_medicament)
        // other code
        val textInfo = dialog.findViewById<TextView>(R.id.tvInfo)
        textInfo.text = medicament.name

        val btnInfo = dialog.findViewById<Button>(R.id.btnInfoMed)
        btnInfo.setOnClickListener {
            val action = FragmentMedicamentListDirections
                .actionFragmentMedicamentListToFragmentMedInfo(medicament.name)
            view?.findNavController()?.navigate(action)
            dialog.cancel()
        }

        val btnReminder = dialog.findViewById<Button>(R.id.btnReminder)
        btnReminder.setOnClickListener {
            val action = FragmentMedicamentListDirections
                .actionFragmentMedicamentListToFragmentReminder(medicament.idMedKit, medicament.idMed)
            view?.findNavController()?.navigate(action)
            dialog.cancel()
        }

        val btnUpdate = dialog.findViewById<Button>(R.id.btnEditMed)
        btnUpdate.setOnClickListener{
            SettingRepository.getInstance().setCurrentMedForKit(medicament)
            view?.findNavController()?.navigate(R.id.action_fragmentMedicamentList_to_fragmentUpdateMedicament)
            dialog.cancel()
        }

        val btnDelete = dialog.findViewById<Button>(R.id.btnDeleteMed)
        btnDelete.setOnClickListener {
            viewModel.deleteMedicament(medicament.idMedKit)
            dialog.cancel()
        }

        val btnMove = dialog.findViewById<Button>(R.id.btnMoveMed)
        btnMove.setOnClickListener {
            dialog.cancel()

            val dialogMove = Dialog(requireContext())
            dialogMove.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMove.setContentView(R.layout.dialog_move_med)

            // ...
            val spinnerKit = dialogMove.findViewById<Spinner>(R.id.spinnerKit)
            val allKit = SettingRepository.getInstance().kitList
            allKit.observe(viewLifecycleOwner){ kitList ->
                val list: MutableList<String> = mutableListOf()
                kitList.forEach { kit ->
                    list.add(kit.name)
                }
                val adapter = ArrayAdapter(requireContext(),
                    android.R.layout.simple_spinner_item, list)
                spinnerKit.adapter = adapter
            }
            val btnMoveMed = dialogMove.findViewById<Button>(R.id.btnMove)
            btnMoveMed.setOnClickListener {
                val kit: Kit = allKit.value!!.get(spinnerKit.selectedItemPosition)
                viewModel.moveMed(kit, medicament)
                dialogMove.cancel()
            }

            dialogMove.show();
            dialogMove.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialogMove.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogMove.window?.setGravity(Gravity.CENTER)
        }

        val btnAccept = dialog.findViewById<Button>(R.id.btnAcceptMed)
        btnAccept.setOnClickListener {
            dialog.cancel()

            val dialogAccept = Dialog(requireContext())
            dialogAccept.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogAccept.setContentView(R.layout.dialog_accept_med)

            val edCount = dialogAccept.findViewById<TextInputEditText>(R.id.textFieldAccept)
            edCount.addTextChangedListener {
                if (edCount.text.toString().toInt() < 0 || edCount.text.toString().toInt() > medicament.count){
                    edCount.error = "Превышение"
                    edCount.focusable
                }
            }

            val btnPlus1 = dialogAccept.findViewById<Button>(R.id.btnPlus1)
            btnPlus1.setOnClickListener {
                val newCount = edCount.text.toString().toInt() + 1
                edCount.setText("$newCount")
            }
            val btnPlus2 = dialogAccept.findViewById<Button>(R.id.btnPlus2)
            btnPlus2.setOnClickListener {
                val newCount = edCount.text.toString().toInt() + 2
                edCount.setText("$newCount")
            }
            val btnMinus1 = dialogAccept.findViewById<Button>(R.id.btnMinus1)
            btnMinus1.setOnClickListener {
                val newCount = edCount.text.toString().toInt() - 1
                edCount.setText("$newCount")
            }
            val btnMinus2 = dialogAccept.findViewById<Button>(R.id.btnMinus2)
            btnMinus2.setOnClickListener {
                val newCount = edCount.text.toString().toInt() - 2
                edCount.setText("$newCount")
            }

            val btnAcceptMed = dialogAccept.findViewById<Button>(R.id.btnAccept)
            btnAcceptMed.setOnClickListener {
                val newCount = medicament.count - edCount.text.toString().toInt()
                val updateMedGroup = MedicationGroup(medicament.idMedKit, medicament.idKit,
                    medicament.idMed, newCount, medicament.expirationDate, medicament.idColor)
                viewModel.updateMedGroup(updateMedGroup)
                dialogAccept.cancel()
            }

            dialogAccept.show();
            dialogAccept.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialogAccept.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogAccept.window?.setGravity(Gravity.CENTER)

        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}