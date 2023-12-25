package com.growthook.aos.presentation.insight.noactionplan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growthook.aos.domain.entity.Cave
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoActionplanInsightViewModel @Inject constructor() :
    ViewModel() {
    var caveList: MutableLiveData<List<Cave>> = MutableLiveData<List<Cave>>()

    init {
        caveList.value = listOf(
            Cave(
                1,
                "aa",
            ),
            Cave(
                2,
                "bb",
            ),
            Cave(
                3,
                "cc",

            ),
            Cave(
                4,
                "dd",

            ),
            Cave(
                5,
                "ee",

            ),
            Cave(
                6,
                "ff",

            ),
            Cave(
                7,
                "gg",

            ),
            Cave(
                8,
                "hh",

            ),
            Cave(
                9,
                "ii",

            ),
            Cave(
                10,
                "jj",

            ),
            Cave(
                11,
                "kk",

            ),
            Cave(
                12,
                "ll",

            ),

        )
    }
}
