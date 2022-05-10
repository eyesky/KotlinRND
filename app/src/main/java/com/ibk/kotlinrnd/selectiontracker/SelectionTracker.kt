package com.ibk.kotlinrnd.selectiontracker

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectionTracker(
    val itemName: String
) : Parcelable
