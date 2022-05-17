package com.ibk.kotlinrnd.generic

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class MyGenericAdapter<T> :
    ListAdapter<T, MyGenericAdapter.ViewHolder<T>>(MyDiffUtilCallback()) {

    var expOnCreateViewHolder: ((ViewGroup) -> ViewBinding)? = null
    var expOnViewHolderBinding: ((T, Int, ViewBinding) -> Unit)? = null

    var selectionTracker: SelectionTracker<Long>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        return expOnCreateViewHolder?.let { it(parent) }
            ?.let { ViewHolder(it, expOnViewHolderBinding) }!!
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        val item = getItem(position)
        holder.bind(
            item,
            position,
            selectionTracker?.isSelected(position.toLong()) ?: false
        )
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ViewHolder<T>(
        private val binding: ViewBinding,
        private val expression: ((T, Int, ViewBinding) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T, position: Int, b: Boolean) {
            expression?.let { it(item, position, binding) }
            itemView.isActivated = b
        }

        fun getItemDetails() = object :
            ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition() = bindingAdapterPosition
            override fun getSelectionKey() = itemId
        }
    }


    class MyDiffUtilCallback<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.toString() == newItem.toString()

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem == newItem
    }

    class GenericItemKeyProvider(private val recyclerView: RecyclerView) : ItemKeyProvider<Long>(
        SCOPE_CACHED
    ) {
        override fun getKey(position: Int) = recyclerView.adapter?.getItemId(position)

        override fun getPosition(key: Long) =
            recyclerView.findViewHolderForItemId(key)?.layoutPosition ?: RecyclerView.NO_POSITION
    }

    class GenericItemLookUp(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y)
            if (view != null)
                return (recyclerView.getChildViewHolder(view) as ViewHolder<*>).getItemDetails()

            return null
        }

    }



}