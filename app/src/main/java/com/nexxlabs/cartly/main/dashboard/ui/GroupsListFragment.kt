package com.nexxlabs.cartly.main.dashboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
        }
    }

    private fun setupRecyclerView() {
        rvAdapter = GroupsAdapter(onGroupClicked = { group ->
            Toast.makeText(requireContext(), "Group clicked: ${group.name}", Toast.LENGTH_SHORT).show()
        })
        binding.groupsRecyclerView.adapter = rvAdapter
        binding.groupsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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

    // Temporary fake data
    private fun mockGroups(): List<Group> = listOf(
        Group("1", "Weekend Groceries", "2024-07-01", listOf(User("1", "","John Doe"), User("2","" ,"Jane Doe"))),
        Group("2", "Birthday Bash", "2024-07-05", listOf(User("1", "","John Doe"), User("2","" ,"Jane Doe")))
    )
}