package com.example.my_first_aid_kit.screen.journal.allList

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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentAllReminderBinding
import com.example.my_first_aid_kit.databinding.FragmentJournalListBinding
import com.example.my_first_aid_kit.models.Reminder
import com.example.my_first_aid_kit.screen.kits.FragmentKitsListDirections
import com.example.my_first_aid_kit.screen.kits.FragmentKitsListViewModel
import com.example.my_first_aid_kit.screen.kits.adapter.RVKitAdapter


class FragmentAllReminder : Fragment(), AllReminderAdapter.Event {

    private lateinit var viewModel: AllReminderViewModel
    private var _binding: FragmentAllReminderBinding? = null
    val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AllReminderViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllReminderBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = AllReminderAdapter(this)
        binding.rvAllReminder.adapter = adapter

        viewModel.allReminder.observe(viewLifecycleOwner){
            viewModel.initListDate()
            adapter.listDate = viewModel.listAllDate
            adapter.reminderList = viewModel.allReminder.value!!
        }

        binding.rvAllReminder.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickReminder(reminder: Reminder) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_reminder)
        // other code
        val textInfo = dialog.findViewById<TextView>(R.id.tvInfoBottom)

        val btnEdit = dialog.findViewById<Button>(R.id.btnEditReminder)
        btnEdit.setOnClickListener{
            dialog.cancel()
        }

        val btnDelete = dialog.findViewById<Button>(R.id.btnDeleteReminder)
        btnDelete.setOnClickListener {
            viewModel.deleteReminder(reminder)
            dialog.cancel()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

}