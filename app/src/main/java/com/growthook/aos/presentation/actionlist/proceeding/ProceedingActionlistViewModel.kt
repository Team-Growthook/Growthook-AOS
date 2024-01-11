package com.growthook.aos.presentation.actionlist.proceeding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growthook.aos.domain.entity.Actionplan

class ProceedingActionlistViewModel : ViewModel() {
    var proceedingActionplanList: MutableLiveData<List<Actionplan>> =
        MutableLiveData<List<Actionplan>>()

    init {
        proceedingActionplanList.value = listOf(
            Actionplan(1, "1북극성 지표를 적용해야 한다", isScraped = true, isFinished = false),
            Actionplan(2, "2목적과 이루어내고 싶은 것 확실하게 하기 (PMF)", isScraped = false, isFinished = false),
            Actionplan(3, "333목적", isScraped = true, isFinished = false),

        )
    }
}
