package com.nexxlabs.cartly.main.groupdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexxlabs.cartly.data.api.model.Item
import com.nexxlabs.cartly.databinding.FragmentItemsListBinding
import timber.log.Timber

class ItemsListFragment : Fragment() {

    private var _binding: FragmentItemsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabType: String
    private lateinit var groupId: String

    private lateinit var itemsAdapter: ItemsAdapter // You'll define this adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabType = arguments?.getString(ARG_TAB_TYPE) ?: "pending"
        groupId = arguments?.getString(ARG_GROUP_ID) ?: ""

        Timber.d("ItemsListFragment created for tab: $tabType, groupId: $groupId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        loadMockData()
    }

    private fun setupRecyclerView() {
        itemsAdapter = ItemsAdapter()
        binding.recyclerView.apply {
            adapter = itemsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadMockData() {
        // Replace with real data later
        val dummyItems = List(10) {
            Item(
                id = it.toString(),
                label = "Item ${it + 1}",
                addedBy = "User $it",
                dateAdded = "2024-07-${10 + it}",
                dateOrdered = if (tabType == "purchased") "2024-07-${15 + it}" else null,
                quantity = it + 1,
                unit = "pcs"
            )
        }
        itemsAdapter.updateData(dummyItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_TAB_TYPE = "tabType"
        private const val ARG_GROUP_ID = "groupId"

        fun newInstance(tab: String, groupId: String): ItemsListFragment {
            return ItemsListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TAB_TYPE, tab)
                    putString(ARG_GROUP_ID, groupId)
                }
            }
        }
    }
}
