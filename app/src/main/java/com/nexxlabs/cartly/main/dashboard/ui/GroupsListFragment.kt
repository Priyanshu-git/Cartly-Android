package com.nexxlabs.cartly.main.dashboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexxlabs.cartly.R
import com.nexxlabs.cartly.data.api.model.Group
import com.nexxlabs.cartly.data.api.model.User
import com.nexxlabs.cartly.databinding.FragmentGroupsListBinding

class GroupsListFragment : Fragment() {

    private var _binding: FragmentGroupsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: GroupsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()
        setupRecyclerView()
        setupListeners()

        // TEMP: Display mock data
        showGroups(mockGroups())
    }

    private fun setupToolbar() {
        binding.topAppBar.apply {
            title = getString(R.string.app_name)
            setTitleTextColor(resources.getColor(R.color.colorPrimary, null))
        }
    }

    private fun setupRecyclerView() {
        rvAdapter = GroupsAdapter(onGroupClicked = { group ->
            handleGroupClick(group)
        })
        binding.groupsRecyclerView.adapter = rvAdapter
        binding.groupsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun handleGroupClick(group: Group) {
        val action = GroupsListFragmentDirections.actionGroupsListToGroupDetailFragment(group.id, group.name)
        findNavController().navigate(action)
    }


    private fun setupListeners() {
        binding.fabAddGroup.setOnClickListener {
            Toast.makeText(requireContext(), "Add Group clicked", Toast.LENGTH_SHORT).show()
        }

        binding.newGroupText.setOnClickListener {
            binding.fabAddGroup.performClick()
        }
    }

    private fun showGroups(groups: List<Group>) {
        if (groups.isEmpty()) {
            binding.groupsRecyclerView.visibility = View.GONE
            binding.emptyState.visibility = View.VISIBLE
        } else {
            rvAdapter.updateData(groups)
            binding.groupsRecyclerView.visibility = View.VISIBLE
            binding.emptyState.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun mockGroups(): List<Group> = listOf(
        Group("1", "Weekend Groceries", "2024-07-01", listOf(User("1", "", "John Doe"), User("2", "", "Jane Doe"))),
        Group("2", "Birthday Bash", "2024-07-05", listOf(User("3", "", "Alice Smith"), User("4", "", "Bob Johnson"))),
        Group("3", "Office Lunch", "2024-07-07", listOf(User("5", "", "Charlie Brown"))),
        Group("4", "Housewarming Party", "2024-07-10", listOf(User("6", "", "Diana Prince"), User("1", "", "John Doe"))),
        Group("5", "Family Dinner", "2024-07-12", listOf(User("7", "", "Bruce Wayne"), User("8", "", "Clark Kent"))),
        Group("6", "Road Trip Supplies", "2024-07-15", listOf(User("9", "", "Peter Parker"))),
        Group("7", "Diwali Shopping", "2024-07-18", listOf(User("10", "", "Natasha Romanoff"))),
        Group("8", "Grocery Split", "2024-07-19", listOf(User("11", "", "Steve Rogers"))),
        Group("9", "Team Outing", "2024-07-20", listOf(User("12", "", "Tony Stark"), User("13", "", "Pepper Potts"))),
        Group("10", "Roommates Kitchen List", "2024-07-21", listOf(User("14", "", "Stephen Strange"))),
        Group("11", "Bachelors Party", "2024-07-22", listOf(User("15", "", "Thor Odinson"), User("16", "", "Loki Laufeyson"))),
        Group("12", "Christmas Gifts", "2024-07-23", listOf(User("17", "", "Wanda Maximoff"))),
        Group("13", "Trip to Goa", "2024-07-24", listOf(User("18", "", "Scott Lang"))),
        Group("14", "Flat Essentials", "2024-07-25", listOf(User("19", "", "Hope Van Dyne"))),
        Group("15", "Raksha Bandhan Sweets", "2024-07-26", listOf(User("20", "", "Shuri"))),
        Group("16", "Durga Puja Festival", "2024-07-27", listOf(User("21", "", "T’Challa"))),
        Group("17", "Gym Meal Prep", "2024-07-28", listOf(User("22", "", "Bucky Barnes"))),
        Group("18", "Movie Night Snacks", "2024-07-29", listOf(User("23", "", "Sam Wilson"))),
        Group("19", "Startup Brainstorming", "2024-07-30", listOf(User("24", "", "Nick Fury"))),
        Group("20", "Wedding Gift Planning", "2024-07-31", listOf(User("25", "", "Carol Danvers")))
    )
}