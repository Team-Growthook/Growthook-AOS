package com.growthook.aos.presentation.actionlist.proceeding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growthook.aos.domain.entity.Actionplan

class ProceedingActionlistViewModel : ViewModel() {
    var proceedingActionplanList: MutableLiveData<List<Actionplan>> =
        MutableLiveData<List<Actionplan>>()

    init {
        proceedingActionplanList.value = listOf(
            Actionplan(1, "1북극성 지표를 적용해야 한다"),
            Actionplan(2, "2목적과 이루어내고 싶은 것 확실하게 하기 (PMF)"),
            Actionplan(3, "3쑥쑥이들이랑 같이 새벽 1시에 불닭을 끓여 갈비 만두와 함께 먹기"),
            Actionplan(4, "4북극성 지표를 적용해야 한다"),
            Actionplan(5, "5목적과 이루어내고 싶은 것 확실하게 하기 (PMF)"),
            Actionplan(6, "6쑥쑥이들이랑 같이 새벽 1시에 불닭을 끓여 갈비 만두와 함께 먹기"),
            Actionplan(7, "7목적과 이루어내고 싶은 것 확실하게 하기 (PMF)"),

        )
    }
}
