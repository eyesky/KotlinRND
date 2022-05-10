package com.ibk.kotlinrnd.selectiontracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ibk.kotlinrnd.R
import kotlinx.android.synthetic.main.fragment_selection_tracker.*

class SelectionTrackerFragment : Fragment(R.layout.fragment_selection_tracker) {

    private val args: SelectionTrackerFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun getList() : MutableList<SelectionTracker> {
        val data = mutableListOf<SelectionTracker>().apply {
            SelectionTracker("a")
            SelectionTracker("b")
            SelectionTracker("c")
            SelectionTracker("d")
            SelectionTracker("e")
            SelectionTracker("f")
            SelectionTracker("g")
            SelectionTracker("h")
            SelectionTracker("i")
            SelectionTracker("j")
        }
        return data
    }


}