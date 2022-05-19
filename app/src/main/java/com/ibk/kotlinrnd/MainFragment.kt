package com.ibk.kotlinrnd

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ibk.kotlinrnd.databinding.FragmentMainBinding
import com.ibk.kotlinrnd.rvselection.RvSelectionViewModel

class MainFragment : Fragment() {
    private val TAG = "MainFragment"
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: RvSelectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        setup()

        return binding.root
    }

    private fun setup() {
        binding.btnSelectionTracker.setOnClickListener {
            val selectionTracker = "Selection Tracker"
            val action =
                MainFragmentDirections.actionMainFragmentToSelectionTrackerFragment(selectionTracker)
            findNavController().navigate(action)
        }

        binding.btnRecyclerviewSelection.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToRvSelectionFragment())
        }

        lifecycleScope.launchWhenCreated {
            viewmodel.selectedList.collect {
                when (it) {
                    is RvSelectionViewModel.RvSelection.SelectedDataList -> {
                        Log.e(TAG, "selected data list: ${it.list} --- size: ${it.list.size}")
                    }
                    else -> Unit
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}