package com.example.my_first_aid_kit.screen.kits

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
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentKitsListBinding
import com.example.my_first_aid_kit.models.Kit
import com.example.my_first_aid_kit.screen.kits.adapter.RVKitAdapter

class FragmentKitsList : Fragment(), RVKitAdapter.EventKit {

    private var _binding: FragmentKitsListBinding? = null
    private lateinit var viewModel: FragmentKitsListViewModel
    val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKitsListBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(FragmentKitsListViewModel::class.java)

        val adapter = RVKitAdapter(this)
        binding.rvKit.adapter = adapter
        viewModel.kitList.observe(viewLifecycleOwner){
            adapter.kitList = it
        }

        binding.rvKit.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickKit(kit: Kit) {
        val action = FragmentKitsListDirections
            .actionFragmentKitsListToFragmentMedicamentList(kit.idKit)
        view?.findNavController()?.navigate(action)
    }

    override fun clickAddKit() {
        view?.findNavController()?.navigate(R.id.action_fragmentKitsList_to_fragmentAddKit, null)
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
            val action = FragmentKitsListDirections
                .actionFragmentKitsListToFragmentUpdateKit(kit.idKit)
            view?.findNavController()?.navigate(action)
            dialog.cancel()
        }

        val btnDelete = dialog.findViewById<Button>(R.id.btnDeleteKit)
        btnDelete.setOnClickListener {
            viewModel.deleteKit(kit)
            dialog.cancel()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}