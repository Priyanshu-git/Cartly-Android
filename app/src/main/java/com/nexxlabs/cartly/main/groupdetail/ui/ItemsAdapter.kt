package com.nexxlabs.cartly.main.groupdetail.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexxlabs.cartly.R
import com.nexxlabs.cartly.data.models.GroceryItem
import com.nexxlabs.cartly.databinding.LayoutItemBinding
import timber.log.Timber

class ItemsAdapter(val tabType: TabType) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private val items = mutableListOf<GroceryItem>()

    val currentList: List<GroceryItem>
        get() = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateData(newList: List<GroceryItem>) {
        val diffCallback = ItemDiffCallback(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
        Timber.d("Items updated: ${items.size}")
    }

    fun removeItem(index: Int) {
        if (index >= 0 && index < items.size) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ItemViewHolder(private val binding: LayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroceryItem) {
            val mContext = binding.root.context
            binding.apply {
                tvItemLabel.text = item.name
                tvItemMeta.text = "${item.quantity} ${item.unit}"
                tvItemAddedBy.text = item.addedByUserId
                tvItemDate.text = if (tabType == TabType.PENDING) item.dateAdded.toString() else item.dateOrdered.toString()

                if (tabType == TabType.PURCHASED) {
                    brandCTA.visibility = View.GONE
                } else {
                    brandCTA.visibility = View.VISIBLE
                    brandCTA.setOnClickListener {
                        try {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                "${mContext.getString(R.string.blinkit_deeplink_url)}${item.name}".toUri()
                            )
                            mContext.startActivity(intent)
                        } catch (e: Exception) {
                            Timber.e(e)
                        }
                    }
                }

            }


        }
    }

    class ItemDiffCallback(
        private val oldList: List<GroceryItem>,
        private val newList: List<GroceryItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}
