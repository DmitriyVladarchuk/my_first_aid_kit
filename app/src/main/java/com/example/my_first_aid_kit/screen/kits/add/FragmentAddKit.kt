package com.example.my_first_aid_kit.screen.kits.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentAddKitBinding
import com.example.my_first_aid_kit.screen.kits.update.FragmentUpdateKitArgs
import com.example.my_first_aid_kit.screen.kits.update.ViewModelFactory


class FragmentAddKit : Fragment() {

    private lateinit var viewModel: FragmentAddKitViewModel
    private var idColor: Int = R.color.blue1
    //private val viewModel: FragmentAddKitViewModel by viewModels()
    private var _binding: FragmentAddKitBinding? = null
    val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddKitBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(FragmentAddKitViewModel::class.java)

        binding.colorBlue1.setOnClickListener{ idColor = R.color.blue1; changeColor() }
        binding.colorBlue2.setOnClickListener{ idColor = R.color.blue2; changeColor() }
        binding.colorCrimson.setOnClickListener{ idColor = R.color.crimson; changeColor() }
        binding.colorPurple.setOnClickListener{ idColor = R.color.purple; changeColor() }
        binding.colorRed.setOnClickListener{ idColor = R.color.red; changeColor() }

        binding.btnAddKit.setOnClickListener {
            viewModel.addKit(binding.textFieldKit.text.toString(), idColor)
            view.findNavController().popBackStack()
        }

        changeColor()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeColor(){
        binding.imageView.setColorFilter(ContextCompat.getColor(requireContext(), idColor))
        binding.boxTextInputKit.boxStrokeColor = ContextCompat.getColor(requireContext(), idColor)
        binding.btnAddKit.setBackgroundColor(ContextCompat.getColor(requireContext(), idColor))
    }

}