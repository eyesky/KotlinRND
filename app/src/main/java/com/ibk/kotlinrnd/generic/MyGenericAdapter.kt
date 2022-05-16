package com.ibk.kotlinrnd.generic

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class MyGenericAdapter<T> :
    ListAdapter<T, MyGenericAdapter.ViewHolder<T>>(MyDiffUtilCallback()) {

    var expOnCreateViewHolder: ((ViewGroup) -> ViewBinding)? = null
    var expOnViewHolderBinding: ((T, ViewBinding) -> Unit)? = null

    class ViewHolder<T>(
        private val binding: ViewBinding,
        private val expression: ((T, ViewBinding) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) = expression?.let { it(item, binding) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        return expOnCreateViewHolder?.let { it(parent) }
            ?.let { ViewHolder(it, expOnViewHolderBinding) }!!
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}


class MyDiffUtilCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.toString() == newItem.toString()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem == newItem
}
