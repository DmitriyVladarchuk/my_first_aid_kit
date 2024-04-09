package com.example.my_first_aid_kit.screen.medicamention

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentKitsListBinding
import com.example.my_first_aid_kit.databinding.FragmentMedicamentionListBinding
import com.example.my_first_aid_kit.models.Kit
import com.example.my_first_aid_kit.models.Medicament
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.repository.SettingRepository
import com.example.my_first_aid_kit.screen.kits.FragmentKitsListDirections
import com.example.my_first_aid_kit.screen.kits.FragmentKitsListViewModel
import com.example.my_first_aid_kit.screen.kits.adapter.RVKitAdapter
import com.example.my_first_aid_kit.screen.kits.update.FragmentUpdateKitArgs
import com.example.my_first_aid_kit.screen.kits.update.FragmentUpdateKitViewModel
import com.example.my_first_aid_kit.screen.kits.update.ViewModelFactory
import com.example.my_first_aid_kit.screen.medicamention.adapter.RVMedicamentAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentMedicamentList : Fragment(), RVMedicamentAdapter.EventMedicament {

    private lateinit var viewModel: FragmentMedicamentListViewModel
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
        viewModel = ViewModelProvider(this, viewModelFactory).get(FragmentMedicamentListViewModel::class.java)

        val idKitA = FragmentMedicamentListArgs.fromBundle(requireArguments()).idKit
        binding.textView5.text = idKitA.toString()

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

        val btnUpdate = dialog.findViewById<Button>(R.id.btnEditMed)
        btnUpdate.setOnClickListener{ view?.findNavController()
            ?.navigate(R.id.action_fragmentMedicamentList_to_fragmentUpdateMedicament)
            dialog.cancel()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}