package com.ibk.kotlinrnd.rvselection

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RvSelectionViewModel : ViewModel() {

    private val _selectedList = MutableStateFlow<RvSelection>(RvSelection.Empty)
    val selectedList = _selectedList.asStateFlow()

    fun selectedItemList(list: List<RvSelectionItem>) {
        _selectedList.value = RvSelection.SelectedDataList(list)
    }

    sealed class RvSelection {
        data class SelectedDataList(val list: List<RvSelectionItem>) : RvSelection()
        object Empty : RvSelection()
    }

}