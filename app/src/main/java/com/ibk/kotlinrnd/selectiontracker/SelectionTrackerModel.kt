package com.ibk.kotlinrnd.selectiontracker

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectionTrackerModel(
    val itemName: String
) : Parcelable
