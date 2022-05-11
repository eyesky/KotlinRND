package com.ibk.kotlinrnd.generic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ibk.kotlinrnd.databinding.ItemSelectionTrackerBinding

class SimpleGenericRecyclerAdapter<T> :
    RecyclerView.Adapter<SimpleGenericRecyclerAdapter.ViewHolder<T>>() {

    var listOfItems:MutableList<T>? = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var expressionViewHolderBinding: ((T, ViewBinding) -> Unit)? = null
    var expressionOnCreateViewHolder: ((ViewGroup) -> ViewBinding)? = null

    class ViewHolder<T>(
        private val binding: ViewBinding,
        private val expression: (T, ViewBinding) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) = expression(item, binding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        return expressionOnCreateViewHolder?.let { it(parent) }?.let { ViewHolder(it, expressionViewHolderBinding!!) }!!
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        val item = listOfItems!![position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listOfItems!!.size
}

