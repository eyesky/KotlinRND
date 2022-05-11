package com.ibk.kotlinrnd.selectiontracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibk.kotlinrnd.R
import com.ibk.kotlinrnd.databinding.FragmentSelectionTrackerBinding
import com.ibk.kotlinrnd.generic.GenericSimpleRecyclerBindingInterface
import com.ibk.kotlinrnd.generic.SimpleGenericRecyclerAdapter

class SelectionTrackerFragment : Fragment(R.layout.fragment_selection_tracker) {

    private lateinit var binding: FragmentSelectionTrackerBinding
    private val args: SelectionTrackerFragmentArgs by navArgs()

    private lateinit var adapter: SimpleGenericRecyclerAdapter<SelectionTrackerModel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectionTrackerBinding.bind(view)

        adapter = SimpleGenericRecyclerAdapter(
            getList(),
            R.layout.item_selection_tracker,
            createInterface()
        )

        binding.rvSelectionTracker.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvSelectionTracker.adapter = adapter


    }

    private fun getList(): MutableList<SelectionTrackerModel> {
        return mutableListOf(
            SelectionTrackerModel("a"),
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

    private fun createInterface() =
        object : GenericSimpleRecyclerBindingInterface<SelectionTrackerModel> {
            override fun bindData(item: SelectionTrackerModel, view: View) {
                view.findViewById<CheckBox>(R.id.checkbox_selection_tracker).text = item.itemName
            }

        }
}
