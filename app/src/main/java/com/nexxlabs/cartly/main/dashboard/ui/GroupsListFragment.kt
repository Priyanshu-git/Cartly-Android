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
import com.nexxlabs.cartly.data.models.ShoppingList
import com.nexxlabs.cartly.data.models.User
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

    private fun handleGroupClick(shoppingList: ShoppingList) {
        val action = GroupsListFragmentDirections.actionGroupsListToGroupDetailFragment(shoppingList.id, shoppingList.name)
        findNavController().navigate(action)
    }


    private fun setupListeners() {
        binding.fabAddGroup.setOnClickListener {
            val bottomSheet = NewGroupBottomSheet { groupName, members ->
                Toast.makeText(requireContext(), "Group created: $groupName", Toast.LENGTH_SHORT).show()
            }
            bottomSheet.show(childFragmentManager, "NewGroupBottomSheet")
        }

        binding.newGroupText.setOnClickListener {
            binding.fabAddGroup.performClick()
        }
    }

    private fun showGroups(shoppingLists: List<ShoppingList>) {
        if (shoppingLists.isEmpty()) {
            binding.groupsRecyclerView.visibility = View.GONE
            binding.emptyState.visibility = View.VISIBLE
        } else {
            rvAdapter.updateData(shoppingLists)
            binding.groupsRecyclerView.visibility = View.VISIBLE
            binding.emptyState.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun mockGroups(): List<ShoppingList> = listOf(
        ShoppingList(
            id = "1",
            name = "Weekend Groceries",
            description = "Essential groceries for the weekend",
            createdBy = "1",
            createdAt = "2024-07-01",
            members = listOf(User("1", "", "John Doe"), User("2", "", "Jane Doe"))
        ),
        ShoppingList(
            id = "2",
            name = "Birthday Bash",
            description = "Shopping list for birthday party supplies",
            createdBy = "3",
            createdAt = "2024-07-05",
            members = listOf(User("3", "", "Alice Smith"), User("4", "", "Bob Johnson"))
        ),
        ShoppingList(
            id = "3",
            name = "Office Lunch",
            description = "Lunch items for office gathering",
            createdBy = "5",
            createdAt = "2024-07-07",
            members = listOf(User("5", "", "Charlie Brown"))
        ),
        ShoppingList(
            id = "4",
            name = "Housewarming Party",
            description = "Items needed for housewarming celebration",
            createdBy = "6",
            createdAt = "2024-07-10",
            members = listOf(User("6", "", "Diana Prince"), User("1", "", "John Doe"))
        ),
        ShoppingList(
            id = "5",
            name = "Family Dinner",
            description = "Ingredients for family dinner preparation",
            createdBy = "7",
            createdAt = "2024-07-12",
            members = listOf(User("7", "", "Bruce Wayne"), User("8", "", "Clark Kent"))
        ),
        ShoppingList(
            id = "6",
            name = "Road Trip Supplies",
            description = "Essential supplies for upcoming road trip",
            createdBy = "9",
            createdAt = "2024-07-15",
            members = listOf(User("9", "", "Peter Parker"))
        ),
        ShoppingList(
            id = "7",
            name = "Diwali Shopping",
            description = "Festival shopping for Diwali celebration",
            createdBy = "10",
            createdAt = "2024-07-18",
            members = listOf(User("10", "", "Natasha Romanoff"))
        ),
        ShoppingList(
            id = "8",
            name = "Grocery Split",
            description = "Shared grocery expenses among roommates",
            createdBy = "11",
            createdAt = "2024-07-19",
            members = listOf(User("11", "", "Steve Rogers"))
        ),
        ShoppingList(
            id = "9",
            name = "Team Outing",
            description = "Supplies for team building outing",
            createdBy = "12",
            createdAt = "2024-07-20",
            members = listOf(User("12", "", "Tony Stark"), User("13", "", "Pepper Potts"))
        ),
        ShoppingList(
            id = "10",
            name = "Roommates Kitchen List",
            description = "Kitchen essentials for shared apartment",
            createdBy = "14",
            createdAt = "2024-07-21",
            members = listOf(User("14", "", "Stephen Strange"))
        ),
        ShoppingList(
            id = "11",
            name = "Bachelors Party",
            description = "Party supplies for bachelor celebration",
            createdBy = "15",
            createdAt = "2024-07-22",
            members = listOf(User("15", "", "Thor Odinson"), User("16", "", "Loki Laufeyson"))
        ),
        ShoppingList(
            id = "12",
            name = "Christmas Gifts",
            description = "Gift shopping list for Christmas",
            createdBy = "17",
            createdAt = "2024-07-23",
            members = listOf(User("17", "", "Wanda Maximoff"))
        ),
        ShoppingList(
            id = "13",
            name = "Trip to Goa",
            description = "Travel essentials for Goa vacation",
            createdBy = "18",
            createdAt = "2024-07-24",
            members = listOf(User("18", "", "Scott Lang"))
        ),
        ShoppingList(
            id = "14",
            name = "Flat Essentials",
            description = "Basic necessities for new apartment",
            createdBy = "19",
            createdAt = "2024-07-25",
            members = listOf(User("19", "", "Hope Van Dyne"))
        ),
        ShoppingList(
            id = "15",
            name = "Raksha Bandhan Sweets",
            description = "Traditional sweets for Raksha Bandhan",
            createdBy = "20",
            createdAt = "2024-07-26",
            members = listOf(User("20", "", "Shuri"))
        ),
        ShoppingList(
            id = "16",
            name = "Durga Puja Festival",
            description = "Items for Durga Puja celebration",
            createdBy = "21",
            createdAt = "2024-07-27",
            members = listOf(User("21", "", "T'Challa"))
        ),
        ShoppingList(
            id = "17",
            name = "Gym Meal Prep",
            description = "Healthy ingredients for meal preparation",
            createdBy = "22",
            createdAt = "2024-07-28",
            members = listOf(User("22", "", "Bucky Barnes"))
        ),
        ShoppingList(
            id = "18",
            name = "Movie Night Snacks",
            description = "Snacks and drinks for movie night",
            createdBy = "23",
            createdAt = "2024-07-29",
            members = listOf(User("23", "", "Sam Wilson"))
        ),
        ShoppingList(
            id = "19",
            name = "Startup Brainstorming",
            description = "Office supplies for startup meeting",
            createdBy = "24",
            createdAt = "2024-07-30",
            members = listOf(User("24", "", "Nick Fury"))
        ),
        ShoppingList(
            id = "20",
            name = "Wedding Gift Planning",
            description = "Collaborative wedding gift shopping",
            createdBy = "25",
            createdAt = "2024-07-31",
            members = listOf(User("25", "", "Carol Danvers"))
        )
    )
}