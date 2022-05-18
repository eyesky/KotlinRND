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

    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

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
            holder.bind(item, it.isSelected(position.toLong()))
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

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): Long? = itemId
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

    class MyItemKeyProvider(private val recyclerView: RecyclerView) :
        ItemKeyProvider<Long>(SCOPE_CACHED) {
        override fun getKey(position: Int): Long? = recyclerView.adapter?.getItemId(position)

        override fun getPosition(key: Long): Int =
            recyclerView.findViewHolderForItemId(key)?.layoutPosition ?: RecyclerView.NO_POSITION
    }

    class MyItemDetailsLookup(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<Long>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as ItemViewHolder).getItemDetails()
            }
            return null
        }
    }
}