package com.ibk.kotlinrnd.selectiontracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibk.kotlinrnd.R
import com.ibk.kotlinrnd.databinding.FragmentSelectionTrackerBinding
import com.ibk.kotlinrnd.databinding.ItemSelectionTrackerBinding
import com.ibk.kotlinrnd.generic.MyGenericAdapter

class SelectionTrackerFragment : Fragment(R.layout.fragment_selection_tracker) {
    private lateinit var selectionItems: List<SelectionTrackerModel>
    private val TAG = "SelectionTrackerFragmen"
    private lateinit var binding: FragmentSelectionTrackerBinding
    private val args: SelectionTrackerFragmentArgs by navArgs()
    private lateinit var mAdapter: MyGenericAdapter<SelectionTrackerModel>
    private lateinit var selectionTracker: SelectionTracker<Long>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectionTrackerBinding.bind(view)

        //Initiate the adapter with type
        mAdapter = MyGenericAdapter()

        mAdapter.expOnViewHolderBinding = { item, pos, viewBinding ->
            //cast the viewBinding with your layout binding class
            var binding = viewBinding as ItemSelectionTrackerBinding
            binding.checkboxSelectionTracker.text = item.itemName
            binding.checkboxSelectionTracker.setOnClickListener {
                Log.e(TAG, "onViewCreated: ${item.itemName} pos: $pos")
            }

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

        selectionTracker = SelectionTracker.Builder(
            "material",
            binding.rvSelectionTracker,
            MyGenericAdapter.GenericItemKeyProvider(binding.rvSelectionTracker),
            MyGenericAdapter.GenericItemLookUp(binding.rvSelectionTracker),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()

        selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>(){
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                if (selectionTracker.selection.size() > 0) {
                    selectionItems = selectionTracker.selection.map {
                        mAdapter.currentList[it.toInt()]
                    }
                    selectionItems.forEach{
                        Log.e(TAG, "onSelectionChanged: ${it.itemName}")
                    }
                }
            }
        })

        mAdapter.selectionTracker = selectionTracker


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


