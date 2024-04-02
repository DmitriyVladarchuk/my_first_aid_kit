package com.example.my_first_aid_kit.screen.setting

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.my_first_aid_kit.App
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentKitsListBinding
import com.example.my_first_aid_kit.databinding.FragmentSettingBinding
import com.example.my_first_aid_kit.repository.SettingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FragmentSetting : Fragment() {

    //private val viewModel: FragmentSettingViewModel by viewModels()
    private lateinit var viewModel: FragmentSettingViewModel
    private var _binding: FragmentSettingBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.buttonNotification.setOnClickListener{
            val shared = context?.getSharedPreferences(SettingRepository.TAG_DARK_MODE, Context.MODE_PRIVATE)
            val editor = shared?.edit()
            editor?.putInt(SettingRepository.TAG_DARK_MODE, SettingRepository.MODE_LIGHT)
            editor?.apply()
            //SettingRepository.getInstance().setTheme()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onAttach(context: Context) {
        viewModel = ViewModelProvider(this).get(FragmentSettingViewModel::class.java)

        super.onAttach(context)
    }

}