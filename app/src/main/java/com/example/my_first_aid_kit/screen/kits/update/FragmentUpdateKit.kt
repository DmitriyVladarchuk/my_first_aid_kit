package com.example.my_first_aid_kit.screen.kits.update

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentAddKitBinding
import com.example.my_first_aid_kit.screen.kits.add.FragmentAddKitViewModel

class FragmentUpdateKit : Fragment() {

    private lateinit var viewModel: FragmentUpdateKitViewModel
    private var idColor: Int = R.color.blue1
    private var _binding: FragmentAddKitBinding? = null
    val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
        val viewModelFactory = ViewModelFactory(
            FragmentUpdateKitArgs.fromBundle(requireArguments()).idKit
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(FragmentUpdateKitViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddKitBinding.inflate(inflater, container, false)
        val view = binding.root



        binding.btnAddKit.text = getText(R.string.update)
        binding.tvInfo.text = getText(R.string.update_kit)

        binding.colorBlue1.setOnClickListener{ idColor = R.color.blue1; changeColor() }
        binding.colorBlue2.setOnClickListener{ idColor = R.color.blue2; changeColor() }
        binding.colorCrimson.setOnClickListener{ idColor = R.color.crimson; changeColor() }
        binding.colorPurple.setOnClickListener{ idColor = R.color.purple; changeColor() }
        binding.colorRed.setOnClickListener{ idColor = R.color.red; changeColor() }

        binding.btnAddKit.setOnClickListener {
            viewModel.updateKit(binding.textFieldKit.text.toString(), idColor)
            view.findNavController().popBackStack()
        }

        viewModel.currentKit.observe(viewLifecycleOwner){
            binding.textFieldKit.setText(viewModel.currentKit.value?.name ?: "")
            idColor = viewModel.currentKit.value?.idColor ?: idColor
            changeColor()
        }

        return view
    }

    override fun onStop() {
        super.onStop()
        viewModel.currentKit.value?.name = binding.textFieldKit.text.toString()
        viewModel.currentKit.value?.idColor = idColor
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