package com.ibk.kotlinrnd.rvselection

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibk.kotlinrnd.R
import com.ibk.kotlinrnd.databinding.FragmentMainBinding
import com.ibk.kotlinrnd.databinding.FragmentRvselectionBinding
import com.ibk.kotlinrnd.selectiontracker.SelectionTrackerModel

class RvSelectionFragment : Fragment(R.layout.fragment_rvselection) {
    private lateinit var selectionItems: List<RvSelectionItem>
    private val TAG = "RvSelectionFragment"
    private lateinit var binding: FragmentRvselectionBinding

    private lateinit var mAdapter: RvSelectionAdapter
    private lateinit var tracker: SelectionTracker<Long>

    private val viewModel: RvSelectionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRvselectionBinding.bind(view)

        setup()
    }

    private fun setup() {
        mAdapter = RvSelectionAdapter()

        binding.rvRecyclerviewSelection.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }

        mAdapter.submitList(getDataList())

        tracker = SelectionTracker.Builder(
            "mySelection",
            binding.rvRecyclerviewSelection,
            RvSelectionAdapter.MyItemKeyProvider(binding.rvRecyclerviewSelection),
            RvSelectionAdapter.MyItemDetailsLookup(binding.rvRecyclerviewSelection),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    if (tracker.selection.size() > 0) {
                        selectionItems = tracker.selection.map {
                            mAdapter.currentList[it.toInt()]
                        }
                        selectionItems.forEach {
                            Log.e(TAG, "onSelectionChanged: ${it}")
                        }
                        viewModel.selectedItemList(selectionItems)
                    }
                }
            })

        mAdapter.tracker = tracker

    }

    private fun getDataList() = mutableListOf(
        RvSelectionItem("a", "Apple"),
        RvSelectionItem("b", "Ball"),
        RvSelectionItem("c", "Cat"),
        RvSelectionItem("d", "Dog")
    )
}