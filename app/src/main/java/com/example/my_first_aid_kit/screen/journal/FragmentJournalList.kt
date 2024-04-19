package com.example.my_first_aid_kit.screen.journal

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.my_first_aid_kit.R
import com.example.my_first_aid_kit.databinding.FragmentJournalListBinding
import com.example.my_first_aid_kit.databinding.FragmentKitsListBinding
import com.example.my_first_aid_kit.repository.SettingRepository
import com.example.my_first_aid_kit.screen.journal.allList.FragmentAllReminder
import com.example.my_first_aid_kit.screen.journal.today.FragmentToday
import com.example.my_first_aid_kit.screen.journal.tomorrow.FragmentTomorrow
import com.example.my_first_aid_kit.screen.kits.FragmentKitsListViewModel
import com.google.android.material.tabs.TabLayout

class FragmentJournalList : Fragment() {

    private var _binding: FragmentJournalListBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJournalListBinding.inflate(inflater, container, false)
        val view = binding.root

        val listFragment: ArrayList<Fragment> = arrayListOf(
            FragmentAllReminder(),
            FragmentToday(),
            FragmentTomorrow()
        )

        val adapter = ViewPagerAdapter(listFragment, requireActivity().supportFragmentManager, lifecycle)

        binding.vpFragment.adapter = adapter

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0 != null) {
                    binding.vpFragment.currentItem = p0.position
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) { }

            override fun onTabReselected(p0: TabLayout.Tab?) { }

        })

        binding.vpFragment.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}