package com.example.my_first_aid_kit.screen.medicamentInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentMedInfoBinding
import com.example.my_first_aid_kit.databinding.FragmentMedicamentionListBinding
import com.example.my_first_aid_kit.models.Medicament
import com.example.my_first_aid_kit.models.MedicamentForKit
import com.example.my_first_aid_kit.repository.SettingRepository
import com.example.my_first_aid_kit.screen.medicamention.FragmentMedicamentListArgs


class FragmentMedInfo : Fragment() {

    private lateinit var medInfo: LiveData<Medicament>
    private var _binding: FragmentMedInfoBinding? = null
    val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = FragmentMedInfoArgs.fromBundle(requireArguments()).idMed
        medInfo = SettingRepository.getInstance().getMedicament(id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMedInfoBinding.inflate(inflater, container, false)
        val view = binding.root

        medInfo.observe(viewLifecycleOwner){
            val medicament = Medicament(it.idMed, it.barcode, it.nameMed, it.releaseForm)
            binding.tvId.visibility = View.VISIBLE
            binding.tvId.text = "Id: ${medInfo.value!!.idMed}"
            binding.tvName.text = medicament.nameMed
            binding.tvBarcode.text = medicament.barcode
            binding.tvReleaseForm.text = medicament.releaseForm
        }

        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }

        return view
    }

}