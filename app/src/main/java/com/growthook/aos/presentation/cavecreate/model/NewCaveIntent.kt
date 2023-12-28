package com.growthook.aos.presentation.cavecreate.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewCaveIntent(
    val name: String = "",
    val introduction: String = ""
) : Parcelable