package com.nexxlabs.cartly.main.groupdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.nexxlabs.cartly.R
import com.nexxlabs.cartly.databinding.FragmentGroupDetailBinding

class GroupDetailFragment : Fragment() {

    private lateinit var binding: FragmentGroupDetailBinding
    private lateinit var adapter: GroupDetailPagerAdapter

    private val args: GroupDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGroupDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvGroupTitle.text = args.groupName
        setupToolbar()
        setupViewPager()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupViewPager() {
        val groupId = args.groupId
        val defaultTab = args.defaultTab

        adapter = GroupDetailPagerAdapter(this, groupId)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_pending)
                else -> getString(R.string.tab_purchased)
            }
        }.attach()

        val defaultIndex = if (defaultTab == "purchased") 1 else 0
        binding.viewPager.setCurrentItem(defaultIndex, false)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Pending" else "Purchased"
        }.attach()
    }
}
