package com.ibk.kotlinrnd.selectiontracker

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.ibk.kotlinrnd.R
import com.ibk.kotlinrnd.databinding.ItemSelectionTrackerBinding
import kotlinx.android.synthetic.main.fragment_selection_tracker.*

class SelectionTrackerFragment : Fragment(R.layout.fragment_selection_tracker) {

    private val args: SelectionTrackerFragmentArgs by navArgs()
    private lateinit var genericAdapter: GenericRecyclerViewAdapter<SelectionTrackerModel, ItemSelectionTrackerBinding>
    private lateinit var selectionTracker: androidx.recyclerview.selection.SelectionTracker<Long>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genericAdapter = GenericRecyclerViewAdapter(R.layout.item_selection_tracker, createBindingInterface())

        selectionTracker = SelectionTracker.Builder(
            "material",
            rv_selection_tracker,
            GenericItemKeyProvider(rv_selection_tracker),
            GenericItemLookUp(rv_selection_tracker),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()

        selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
//                menuView.findItem(R.id.item_check).isVisible = !selectionTracker.selection.isEmpty
                if (selectionTracker.selection.size() > 0) {
                    selectedItems = selectionTracker.selection.map {
                        genericAdapter.currentList[it.toInt()]
                    }
                    selectedItems.forEach {
                        Log.i("items", "$it")
                    }
                }
            }

        })
        genericAdapter.selectionTracker = selectionTracker

    }

    private fun getList() : MutableList<SelectionTrackerModel> {
        val data = mutableListOf<SelectionTrackerModel>().apply {
            SelectionTrackerModel("a")
            SelectionTrackerModel("b")
            SelectionTrackerModel("c")
            SelectionTrackerModel("d")
            SelectionTrackerModel("e")
            SelectionTrackerModel("f")
            SelectionTrackerModel("g")
            SelectionTrackerModel("h")
            SelectionTrackerModel("i")
            SelectionTrackerModel("j")
        }
        return data
    }

    private fun createBindingInterface() =
        object : GenericRecyclerBindingInterface<ItemSelectionTrackerBinding, SelectionTrackerModel> {
            override fun bindData(
                binder: ItemSelectionTrackerBinding,
                model: SelectionTrackerModel,
                clickListener: GenericClickListener<SelectionTrackerModel>?,
                position: Int
            ) {
                binder.checkboxSelectionTracker.text = model.itemName
            }

        }


}