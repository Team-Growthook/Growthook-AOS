package com.growthook.aos.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CaveModifyIntent(
    val caveId: Int,
    val name: String,
    val introduction: String,
) : Parcelable
