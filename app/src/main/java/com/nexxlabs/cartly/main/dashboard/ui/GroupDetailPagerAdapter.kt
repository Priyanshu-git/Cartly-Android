package com.nexxlabs.cartly.main.dashboard.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class GroupDetailPagerAdapter(
    fragment: Fragment,
    private val groupId: String
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val tab = if (position == 0) "pending" else "purchased"
        return ItemsListFragment.newInstance(tab, groupId)
    }
}
