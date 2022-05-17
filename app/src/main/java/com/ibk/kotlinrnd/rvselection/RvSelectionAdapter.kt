package com.ibk.kotlinrnd.rvselection

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ibk.kotlinrnd.R

class RvSelectionAdapter :
    ListAdapter<RvSelectionItem, RvSelectionAdapter.ItemViewHolder>(DiffCallback()) {

    var tracker: SelectionTracker<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rvselection, parent, false)
        )
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        tracker?.let {
            holder.bind(item, it.isSelected(position.toString()))
        }
    }

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: RvSelectionItem, isActivated: Boolean) {
            view.findViewById<TextView>(R.id.tv_title).text = item.title
            view.findViewById<TextView>(R.id.tv_description).text = item.description
            if (isActivated)
                view.findViewById<ImageView>(R.id.iv_checked_unchecked)
                    .setBackgroundResource(R.drawable.ic_check_circle)
            else
                view.findViewById<ImageView>(R.id.iv_checked_unchecked)
                    .setBackgroundResource(R.drawable.ic_radio_button_unchecked)
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<String> =
            object : ItemDetailsLookup.ItemDetails<String>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): String? = itemId.toString()
                override fun inSelectionHotspot(e: MotionEvent): Boolean = true
            }
    }

    class DiffCallback : DiffUtil.ItemCallback<RvSelectionItem>() {
        override fun areItemsTheSame(oldItem: RvSelectionItem, newItem: RvSelectionItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: RvSelectionItem,
            newItem: RvSelectionItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    class MyItemKeyProvider(private val adapter: RvSelectionAdapter) :
        ItemKeyProvider<String>(SCOPE_CACHED) {
        override fun getKey(position: Int): String? =
            adapter.currentList[position].title

        override fun getPosition(key: String): Int =
            adapter.currentList.indexOfFirst { it.title == key }
    }

    class MyItemDetailsLookup(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<String>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<String>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as RvSelectionAdapter.ItemViewHolder).getItemDetails()
            }
            return null
        }
    }
}