package com.growthook.aos.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewStorage(
    val name: String = "",
    val introduction: String = ""
) : Parcelable