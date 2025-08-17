package com.nexxlabs.cartly.main.groupdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexxlabs.cartly.R
import com.nexxlabs.cartly.data.models.GroceryItem
import com.nexxlabs.cartly.databinding.FragmentItemsListBinding
import timber.log.Timber

class ItemsListFragment : Fragment() {

    private var _binding: FragmentItemsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabType: TabType
    private lateinit var groupId: String

    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabType = if (arguments?.getString(ARG_TAB_TYPE) == "pending")  TabType.PENDING else TabType.PURCHASED
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
        itemsAdapter = ItemsAdapter(tabType)
        binding.recyclerView.apply {
            adapter = itemsAdapter
            layoutManager = LinearLayoutManager(requireContext())

            setupItemSwipeActions(this)
        }
    }

    private fun setupItemSwipeActions(recyclerView: RecyclerView) {
        val swipeCallback = ItemSwipeCallback.Builder(recyclerView.context)
            .setLeftSwipe(R.color.colorSecondary, R.drawable.rounded_add_24)
            .setRightSwipe(R.color.colorError, R.drawable.rounded_arrow_back_24)
            .setOnSwipeListener { position, direction ->
                val item = itemsAdapter.currentList[position]
                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        Timber.d("GroceryItem swiped right: $item")
                        itemsAdapter.removeItem(position)
                    }

                    ItemTouchHelper.LEFT -> {
                        Timber.d("GroceryItem swiped left: $item")
                        itemsAdapter.removeItem(position)
                    }
                }
            }
            .build()

        ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView)
    }

    private fun loadMockData() {
        val dummyItems = if (tabType == TabType.PURCHASED) {
            List(10) {
                GroceryItem(
                    id = it.toString(),
                    name = listOf("Aashirvaad Atta", "Amul Milk", "Colgate Paste", "Tata Salt", "Maggie Noodles", "Pepsi Bottle", "Fortune Oil", "Parle-G Biscuits", "Dove Shampoo", "Surf Excel")[it],
                    addedByUserId = "User $it",
                    dateAdded = "2024-07-${10 + it}",
                    dateOrdered = "2024-07-${15 + it}",
                    quantity = (1..10).random(),
                    unit = "pcs"
                )
            }
        } else {
            List(10) {
                GroceryItem(
                    id = it.toString(),
                    name = listOf("Tropicana Juice", "Amul Butter", "Kissan Jam", "Tata Tea", "MDH Masala", "Britannia Cake", "Dettol Soap", "Red Label Tea", "Nestle Munch", "Good Day Cookies")[it],
                    addedByUserId = "User $it",
                    dateAdded = "2024-07-${10 + it}",
                    dateOrdered = null,
                    quantity = (1..3).random(),
                    unit = "pcs"
                )
            }
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
