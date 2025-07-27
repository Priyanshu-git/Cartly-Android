package com.nexxlabs.cartly.main.dashboard.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexxlabs.cartly.data.api.model.Item
import com.nexxlabs.cartly.databinding.LayoutItemBinding
import timber.log.Timber

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private val items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateData(newList: List<Item>) {
        val diffCallback = ItemDiffCallback(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
        Timber.d("Items updated: ${items.size}")
    }

    inner class ItemViewHolder(private val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.tvItemLabel.text = item.label
            binding.tvItemMeta.text = "${item.quantity} ${item.unit}"
            binding.tvItemAddedBy.text = item.addedBy
            binding.tvItemDate.text = item.dateOrdered ?: item.dateAdded
        }
    }

    class ItemDiffCallback(
        private val oldList: List<Item>,
        private val newList: List<Item>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}
