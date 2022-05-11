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
import com.ibk.kotlinrnd.generic.SimpleGenericRecyclerAdapter

class SelectionTrackerFragment : Fragment(R.layout.fragment_selection_tracker) {

    private lateinit var binding: FragmentSelectionTrackerBinding
    private val args: SelectionTrackerFragmentArgs by navArgs()

    private lateinit var adapter: SimpleGenericRecyclerAdapter<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectionTrackerBinding.bind(view)


        adapter = SimpleGenericRecyclerAdapter()

        //Sample data
        adapter.listOfItems = mutableListOf("2", "3", "2", "3", "2", "3")

        adapter.expressionViewHolderBinding = { eachItem, viewBinding ->
            //eachItem will provide the each item in the list, in this case its a string type
            //cast the viewBinding with yout layout binding class
            var view = viewBinding as ItemSelectionTrackerBinding
            view.checkboxSelectionTracker.text = eachItem
            //you can use click listener on root or any button
            view.root.setOnClickListener {
                //Click item value is eachItem
            }
        }

        adapter.expressionOnCreateViewHolder = { viewGroup ->
            //Inflate the layout and send it to the adapter
            ItemSelectionTrackerBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        }

        binding.rvSelectionTracker.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
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

}


