package com.growthook.aos.presentation.insight.actionplan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growthook.aos.domain.entity.Actionplan

class ActionplanInsightViewModel : ViewModel() {
    var actionplanList: MutableLiveData<List<Actionplan>> = MutableLiveData<List<Actionplan>>()

    init {
        actionplanList.value = listOf(
            Actionplan(1, 1, "아"),
            Actionplan(2, 2, "아아"),
            Actionplan(3, 3, "아아아"),
            Actionplan(4, 4, "아아아아"),
            Actionplan(5, 5, "아아아아아"),
            Actionplan(6, 6, "아아아아아아"),
            Actionplan(7, 7, "아아아아아아아"),
            Actionplan(8, 8, "아아아아아아아아"),
            Actionplan(9, 9, "아아아아아아아아아"),

        )
    }

    fun deleteActionplan(position: Int) {
        val currentList = actionplanList.value.orEmpty().toMutableList()
        if (position in currentList.indices) {
            currentList.removeAt(position)
            actionplanList.value = currentList.toList()
        }
    }
}
