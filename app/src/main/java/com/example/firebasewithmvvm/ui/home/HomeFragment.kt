package com.example.firebasewithmvvm.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.firebasewithmvvm.R
import com.example.firebasewithmvvm.databinding.FragmentHomeBinding
import com.example.firebasewithmvvm.ui.auth.AuthViewModel
import com.example.firebasewithmvvm.util.HomeTabs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    val TAG: String = "HomeFragment"
    lateinit var binding: FragmentHomeBinding
    val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logout.setOnClickListener {
            authViewModel.logout {
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }

        binding.viewPager2.adapter = HomePagerAdapter(this)
        viewPager2SetupWithTabLayout(
            tabLayout = binding.tabLayout,
            viewPager2 = binding.viewPager2
        )
        binding.tabLayout.onTabSelectionListener()
    }

    private fun viewPager2SetupWithTabLayout(tabLayout: TabLayout, viewPager2: ViewPager2){
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.item_tab_layout, null)
            val textViewTitle: TextView = view.findViewById<TextView>(R.id.tabTitle)
            when (position) {
                HomeTabs.NOTES.index -> {
                    tab.customView = view
                    textViewTitle.text = getString(R.string.notes)
                    tab.onSelection(true)
                }
                HomeTabs.TASKS.index -> {
                    tab.customView = view
                    textViewTitle.text = getString(R.string.tasks)
                    tab.onSelection(false)
                }
            }
        }.attach()
    }

}

inline fun TabLayout.onTabSelectionListener(
    crossinline onTabSelected: (TabLayout.Tab?) -> Unit = {},
    crossinline onTabUnselected: (TabLayout.Tab?) -> Unit? = {},
) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.onSelection(true)
            onTabSelected.invoke(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            tab?.onSelection(false)
            onTabUnselected.invoke(tab)
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {}
    })
}

fun TabLayout.Tab.onSelection(isSelected: Boolean = true) {
    val textViewTitle: TextView? = customView?.findViewById<TextView>(R.id.tabTitle)
    textViewTitle?.let { textView ->
        textView.setTextColor(
            ContextCompat.getColor(
                textView.context,
                if (isSelected) R.color.tab_selected else R.color.tab_unselected
            )
        )
    }
}