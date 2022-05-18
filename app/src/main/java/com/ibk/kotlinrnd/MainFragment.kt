package com.ibk.kotlinrnd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ibk.kotlinrnd.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

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
            val action = MainFragmentDirections.actionMainFragmentToSelectionTrackerFragment(selectionTracker)
            findNavController().navigate(action)
        }

        binding.btnRecyclerviewSelection.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToRvSelectionFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}