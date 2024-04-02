package com.example.my_first_aid_kit.screen.kits

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
import com.example.my_first_aid_kit.models.Kit
import com.example.my_first_aid_kit.screen.kits.adapter.RVKitAdapter

class FragmentKitsList : Fragment(), RVKitAdapter.EventKit {

    private val viewModel: FragmentKitsListViewModel by viewModels()
    private var _binding: FragmentKitsListBinding? = null
    val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKitsListBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = RVKitAdapter(this)
        val kitLis: List<Kit> = listOf(
            Kit(0, "One", R.color.blue1),
            Kit(1, "Two", R.color.blue2),
            Kit(2, "Tree", R.color.purple)
        )
        adapter.kitList = kitLis
        binding.rvKit.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvKit.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickKit(position: Int) {
        val action = FragmentKitsListDirections
            .actionFragmentKitsListToFragmentMedicamentList(1)
        view?.findNavController()?.navigate(action)
    }

    override fun clickAddKit() {
        view?.findNavController()?.navigate(R.id.action_fragmentKitsList_to_fragmentAddKit)
    }

    override fun longClickKit(kit: Kit) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_kit)
        // other code
        val textInfo = dialog.findViewById<TextView>(R.id.tvInfoBottom)
        textInfo.text = kit.name

        val btnEdit = dialog.findViewById<Button>(R.id.btnEditKit)
        btnEdit.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_fragmentKitsList_to_fragmentUpdateKit)
            dialog.cancel()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}