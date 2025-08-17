package com.nexxlabs.cartly.main.dashboard.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexxlabs.cartly.R
import com.nexxlabs.cartly.data.models.ShoppingList
import com.nexxlabs.cartly.databinding.ItemGroupBinding
import timber.log.Timber

class GroupsAdapter(
    private val onGroupClicked: (ShoppingList) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<ShoppingList>()
    private enum class VIEW_TYPE{ITEM, FOOTER}

    fun updateData(newList: List<ShoppingList>) {
        val diffCallback = GroupDiffCallback(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
        Timber.d("Updated group list: ${newList.size} items")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE.ITEM.ordinal) {
            val binding = ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return GroupViewHolder(binding)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.view_footer, parent, false)
            return FooterViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Timber.d("onBindViewHolder: $position")
        if (holder is GroupViewHolder && position < items.size) {
            holder.bind(items[position])
        } else if (holder is FooterViewHolder){
            Timber.d("Rendering footer on position: $position")
        }
    }

    override fun getItemCount(): Int {
        Timber.d("getItemCount: ${items.size}")
        return items.size +1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount -1) return VIEW_TYPE.FOOTER.ordinal
        return VIEW_TYPE.ITEM.ordinal
    }

    inner class GroupViewHolder(private val binding: ItemGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shoppingList: ShoppingList) {
            Timber.d("Binding group: ${shoppingList.name} ${shoppingList.members.size}")
            binding.tvGroupName.text = shoppingList.name
            binding.tvGroupMembers.text = "${shoppingList.members.size} members"
            binding.tvGroupMeta.text = "Dummy" // Update if needed

            binding.root.setOnClickListener {
                Timber.d("Clicked item: ${shoppingList.name}")
                onGroupClicked(shoppingList)
            }
        }
    }

    inner class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class GroupDiffCallback(
        private val oldList: List<ShoppingList>,
        private val newList: List<ShoppingList>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
