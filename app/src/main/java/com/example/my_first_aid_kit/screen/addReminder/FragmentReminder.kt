package com.example.my_first_aid_kit.screen.addReminder

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import com.example.my_first_aid_kit.databinding.FragmentReminderBinding
import com.example.my_first_aid_kit.repository.SettingRepository
import com.example.my_first_aid_kit.utill.ValidateData
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class FragmentReminder : Fragment() {

    private lateinit var viewModel: ReminderViewModel
    private var _binding: FragmentReminderBinding? = null
    val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)
        viewModel.newReminder.idMedKit = FragmentReminderArgs.fromBundle(requireArguments()).idMedKit
        viewModel.newReminder.idMed = FragmentReminderArgs.fromBundle(requireArguments()).idMed
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.spinnerTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when(position){
                    0 -> {
                        binding.btnTime1.visibility = View.VISIBLE
                        binding.btnTime2.visibility = View.GONE
                        binding.btnTime3.visibility = View.GONE
                    }
                    1 -> {
                        binding.btnTime1.visibility = View.VISIBLE
                        binding.btnTime2.visibility = View.VISIBLE
                        binding.btnTime3.visibility = View.GONE
                    }
                    2 -> {
                        binding.btnTime1.visibility = View.VISIBLE
                        binding.btnTime2.visibility = View.VISIBLE
                        binding.btnTime3.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) { }
        }

        binding.btnTime1.setOnClickListener {
            val picker = getTimePicker()
            picker.show(requireActivity().supportFragmentManager, "fragment_tag")

            picker.addOnPositiveButtonClickListener {
                viewModel.newReminder.timeInfo = picker.hour.toString() + ":" + picker.minute
                binding.btnTime1.text = viewModel.newReminder.timeInfo
            }
        }

//        binding.btnTime2.setOnClickListener {
//            val picker = getTimePicker()
//            picker.show(requireActivity().supportFragmentManager, "fragment_tag")
//
//            picker.addOnPositiveButtonClickListener {
//                viewModel.time2 = picker.hour.toString() + ":" + picker.minute
//                binding.btnTime2.text = viewModel.time2
//            }
//        }
//
//        binding.btnTime3.setOnClickListener {
//            val picker = getTimePicker()
//            picker.show(requireActivity().supportFragmentManager, "fragment_tag")
//
//            picker.addOnPositiveButtonClickListener {
//                viewModel.time3 = picker.hour.toString() + ":" + picker.minute
//                binding.btnTime3.text = viewModel.time3
//            }
//        }

        binding.btnStartDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                viewModel.newReminder.startReminder = ValidateData.getInstance().collectOnDate(dayOfMonth,month,year)
                binding.btnStartDate.text = viewModel.newReminder.startReminder
            }, year, month, day)
            dpd.show()
        }

        binding.btnStopDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                viewModel.newReminder.stopReminder = ValidateData.getInstance().collectOnDate(dayOfMonth,month,year)
                binding.btnStopDate.text = viewModel.newReminder.stopReminder
            }, year, month, day)
            dpd.show()
        }

        binding.btnAddReminder.setOnClickListener {
            viewModel.addReminder()
            view.findNavController().popBackStack()
        }

//        val allReminder = SettingRepository.getInstance().getAllReminders().asLiveData()
//        allReminder.observe(viewLifecycleOwner){
//            for(i in it){
//               Log.d("TestReminder", i.idReminder.toString())
//            }
//        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTimePicker(): MaterialTimePicker{
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(10)
                .build()
        return picker
    }
}