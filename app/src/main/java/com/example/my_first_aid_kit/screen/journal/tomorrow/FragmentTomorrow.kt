package com.example.my_first_aid_kit.screen.journal.tomorrow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentTodayBinding
import com.example.my_first_aid_kit.screen.journal.today.TodayAdapter
import com.example.my_first_aid_kit.screen.journal.today.TodayViewModel


class FragmentTomorrow : Fragment() {

    private lateinit var viewModel: TomorrowViewModel
    private var _binding: FragmentTodayBinding? = null
    val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(TomorrowViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = TodayAdapter()
        binding.rvRemindersToday.adapter = adapter

        viewModel.allReminders.observe(viewLifecycleOwner){
            adapter.remindersToday = viewModel.getRemindersToday()
        }

        binding.rvRemindersToday.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return view
    }
}