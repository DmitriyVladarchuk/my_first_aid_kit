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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentKitsListBinding
import com.example.my_first_aid_kit.databinding.FragmentMedicamentionListBinding
import com.example.my_first_aid_kit.models.Kit
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.screen.kits.adapter.RVKitAdapter
import com.example.my_first_aid_kit.screen.medicamention.adapter.RVMedicamentAdapter

class FragmentMedicamentList : Fragment(), RVMedicamentAdapter.EventMedicament {

    private val viewModel: FragmentMedicamentListViewModel by viewModels()
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
        binding.textView5.text = idKit.toString()

        val adapter = RVMedicamentAdapter(this)
        val medicamention: List<MedicamentForKit> = listOf(
            MedicamentForKit(0, 0, 0, "One", 0,
                5, "05.05.2025", R.color.purple),
            MedicamentForKit(0, 0, 0, "Two", 1,
                7, "07.07.2027", R.color.blue1)
        )
        adapter.medicamentList = medicamention
        binding.rvMedicament.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMedicament.adapter = adapter

        binding.addMedicamentButton.setOnClickListener{
            view.findNavController().navigate(R.id.action_fragmentMedicamentList_to_fragmentAddMedicament)
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