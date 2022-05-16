package com.ibk.kotlinrnd.generic

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ibk.kotlinrnd.databinding.ItemSelectionTrackerBinding
import kotlin.math.exp

class MyGenericAdapter<T, VM : ViewBinding>(
    private val dataSet: List<T>,
    @LayoutRes val layoutID: Int,
    private val bindingInterface: BindingInterface<T, VM>
) : RecyclerView.Adapter<MyGenericAdapter.ViewHolder>() {

    var expOnCreateViewHolder: ((ViewGroup)-> ViewBinding)? = null

    class ViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
        fun <T, VM : ViewBinding> bind(item: T, bindingInterface: BindingInterface<T, VM>) =
            bindingInterface.bindData(item, binding as VM)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
/*        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            layoutID,
            parent,
            false
        )
        return ViewHolder(binding)*/

        return expOnCreateViewHolder?.let { it(parent) }?.let { ViewHolder(it) }!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item, bindingInterface)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}

interface BindingInterface<T, VM : ViewBinding> {
    fun bindData(item: T, view: VM)
}