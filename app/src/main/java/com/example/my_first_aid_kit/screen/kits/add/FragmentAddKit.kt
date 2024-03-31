package com.example.my_first_aid_kit.screen.kits.add

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.my_first_aid_kit.R

class FragmentAddKit : Fragment() {

    companion object {
        fun newInstance() = FragmentAddKit()
    }

    private val viewModel: FragmentAddKitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_kit, container, false)
    }
}