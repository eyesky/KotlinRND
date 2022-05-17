package com.ibk.kotlinrnd.rvselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.ibk.kotlinrnd.R
import com.ibk.kotlinrnd.databinding.FragmentMainBinding
import com.ibk.kotlinrnd.databinding.FragmentRvselectionBinding
import com.ibk.kotlinrnd.selectiontracker.SelectionTrackerModel

class RvSelectionFragment : Fragment(R.layout.fragment_rvselection) {

    private var _binding: FragmentRvselectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RvSelectionAdapter
    private var selectionTracker: SelectionTracker<String>? = null

/*    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRvselectionBinding.inflate(inflater, container, false)

//        setup()

        return binding.root
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRvselectionBinding.bind(view)

        setup()
    }

    private fun setup() {
        adapter = RvSelectionAdapter()

        selectionTracker = SelectionTracker.Builder(
            "mySelectionx",
            binding.rvRecyclerviewSelection,
            RvSelectionAdapter.MyItemKeyProvider(adapter),
            RvSelectionAdapter.MyItemDetailsLookup(binding.rvRecyclerviewSelection),
            StorageStrategy.createStringStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        selectionTracker?.addObserver(
            object : SelectionTracker.SelectionObserver<String>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    val items = selectionTracker?.selection!!.size()
                }
            })

        adapter.tracker = selectionTracker

        adapter.submitList(getDataList())
    }

    private fun getDataList() = mutableListOf(
        RvSelectionItem("a", "Apple"),
        RvSelectionItem("b", "Ball"),
        RvSelectionItem("c", "Cat"),
        RvSelectionItem("d", "Dog")
    )

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}