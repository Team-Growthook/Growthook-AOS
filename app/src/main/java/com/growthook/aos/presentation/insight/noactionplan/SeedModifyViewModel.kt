package com.growthook.aos.presentation.insight.noactionplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growthook.aos.domain.entity.SeedInfo

class SeedModifyViewModel: ViewModel() {

    private val _seedInfo: MutableLiveData<SeedInfo> = MutableLiveData(
       SeedInfo(
           "작성완",
           null,
           "선택완",
           "출처만 입력한 경우",
           null,
           1
       )
    )

    val seedInfo: LiveData<SeedInfo>
        get() = _seedInfo

}