package com.growthook.aos.presentation.insight.noactionplan.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeedModifyIntent(
    val seedId: Int = 0,
    val insight: String = "",
    val memo: String?,
    val cave: String = "",
    val source: String = "",
    val url: String?,
    val goalMonth: Int = 0,
) : Parcelable
