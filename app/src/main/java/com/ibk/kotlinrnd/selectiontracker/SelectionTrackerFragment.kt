package com.ibk.kotlinrnd.selectiontracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibk.kotlinrnd.R
import com.ibk.kotlinrnd.databinding.FragmentSelectionTrackerBinding
import com.ibk.kotlinrnd.databinding.ItemSelectionTrackerBinding
import com.ibk.kotlinrnd.generic.MyGenericAdapter

class SelectionTrackerFragment : Fragment(R.layout.fragment_selection_tracker) {

    private lateinit var binding: FragmentSelectionTrackerBinding
    private val args: SelectionTrackerFragmentArgs by navArgs()
    private lateinit var mAdapter: MyGenericAdapter<SelectionTrackerModel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectionTrackerBinding.bind(view)

        //Initiate the adapter with type
        mAdapter = MyGenericAdapter()

        mAdapter.expOnViewHolderBinding = { item, viewBinding ->
            //cast the viewBinding with your layout binding class
            var binding = viewBinding as ItemSelectionTrackerBinding
            binding.checkboxSelectionTracker.text = item.itemName
        }

        //Inflate the layout and send it to the adapter
        mAdapter.expOnCreateViewHolder = {
            ItemSelectionTrackerBinding.inflate(LayoutInflater.from(it.context), it, false)
        }

        //finally put the adapter to recyclerview
        binding.rvSelectionTracker.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }

        // here pass the array list data into adapter
        mAdapter.submitList(getList())

    }

    private fun getList(): MutableList<SelectionTrackerModel> {
        return mutableListOf(
            SelectionTrackerModel("araf mollah"),
            SelectionTrackerModel("b"),
            SelectionTrackerModel("c"),
            SelectionTrackerModel("d"),
            SelectionTrackerModel("e"),
            SelectionTrackerModel("f"),
            SelectionTrackerModel("g"),
            SelectionTrackerModel("h"),
            SelectionTrackerModel("i"),
            SelectionTrackerModel("j")
        )
    }

}


