package com.growthook.aos.presentation.actionlist.completed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growthook.aos.domain.entity.Actionplan

class CompletedActionlistViewModel : ViewModel() {
    var completedActionplanList: MutableLiveData<List<Actionplan>> =
        MutableLiveData<List<Actionplan>>()

    init {
        completedActionplanList.value = listOf(
            Actionplan(1, "1완", isScraped = true, isFinished = false),
            Actionplan(2, "2완", isScraped = false, isFinished = false),
        )
    }
}
