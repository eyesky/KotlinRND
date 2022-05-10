package com.ibk.kotlinrnd.selectiontracker

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectionTracker(
    private val itemName: String
) : Parcelable
