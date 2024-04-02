package com.example.my_first_aid_kit.screen.kits.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentAddKitBinding


class FragmentAddKit : Fragment() {

    private val viewModel: FragmentAddKitViewModel by viewModels()
    private var _binding: FragmentAddKitBinding? = null
    private var idColor: Int = R.color.blue1
    val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddKitBinding.inflate(inflater, container, false)
        val view = binding.root
        changeColor()

        binding.colorBlue1.setOnClickListener{ idColor = R.color.blue1; changeColor() }
        binding.colorBlue2.setOnClickListener{ idColor = R.color.blue2; changeColor() }
        binding.colorCrimson.setOnClickListener{ idColor = R.color.crimson; changeColor() }
        binding.colorPurple.setOnClickListener{ idColor = R.color.purple; changeColor() }
        binding.colorRed.setOnClickListener{ idColor = R.color.red; changeColor() }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeColor(){
        binding.imageView.setColorFilter(ContextCompat.getColor(requireContext(), idColor))
        binding.boxTextInputKit.boxStrokeColor = ContextCompat.getColor(requireContext(), idColor)
        binding.button.setBackgroundColor(ContextCompat.getColor(requireContext(), idColor))
    }

}