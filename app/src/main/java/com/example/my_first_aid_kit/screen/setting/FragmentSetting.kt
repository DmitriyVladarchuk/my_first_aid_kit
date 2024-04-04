package com.example.my_first_aid_kit.screen.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentSettingBinding
import com.example.my_first_aid_kit.repository.SettingRepository


class FragmentSetting : Fragment() {

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

        binding.switchDarkMode.isChecked = viewModel.setSwitchDarkMode()

        when(viewModel.backgroundWork){
            SettingRepository.BACKGROUND_WORK_ONE -> binding.rbBut1.isChecked = true
            SettingRepository.BACKGROUND_WORK_TWO -> binding.rbBut2.isChecked = true
            SettingRepository.BACKGROUND_WORK_TREE -> binding.rbBut3.isChecked = true
            SettingRepository.BACKGROUND_WORK_FOUR-> binding.rbBut4.isChecked = true
        }

        binding.backgroundWorkGroup.setOnCheckedChangeListener { _, id ->
            when(id){
                R.id.rb_but_1 -> viewModel.changeBackgroundWork(SettingRepository.BACKGROUND_WORK_ONE)
                R.id.rb_but_2 -> viewModel.changeBackgroundWork(SettingRepository.BACKGROUND_WORK_TWO)
                R.id.rb_but_3 -> viewModel.changeBackgroundWork(SettingRepository.BACKGROUND_WORK_TREE)
                R.id.rb_but_4 -> viewModel.changeBackgroundWork(SettingRepository.BACKGROUND_WORK_FOUR)
            }
        }

        binding.buttonNotification.setOnClickListener{
            val intent = Intent()
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("android.provider.extra.APP_PACKAGE", requireContext().packageName)
            startActivity(intent)
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, _ ->
            viewModel.changeDarkMode()
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