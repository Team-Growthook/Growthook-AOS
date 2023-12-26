package com.growthook.aos.presentation.insight.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growthook.aos.domain.entity.Cave
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InsightWriteViewModel @Inject constructor() : ViewModel() {

    private val _selectedCaveName: MutableLiveData<String> = MutableLiveData()
    val selectedCaveName : LiveData<String>
        get() = _selectedCaveName

    fun setSelectedCaveName(caveName: String) {
        _selectedCaveName.value = caveName
    }

    private val _selectedGoalMonth: MutableLiveData<Int> = MutableLiveData()
    val selectedGoalMonth: LiveData<Int>
        get() = _selectedGoalMonth

    fun setSelectedGoalMonth(goalMonth: Int) {
        _selectedGoalMonth.value = goalMonth
    }


    private val _mockCaveList: MutableLiveData<List<Cave>> = MutableLiveData(
        mutableListOf(
            Cave(
                1,
                "test1"
            ),
            Cave(
                2,
                "test2"
            ),
            Cave(
                3,
                "test3"
            ),
            Cave(
                4,
                "test4"
            ),
            Cave(
                5,
                "test5"
            ),
            Cave(
                6,
                "test6"
            ),
            Cave(
                7,
                "test7"
            )
        )
    )

    val mockCaveList: LiveData<List<Cave>>
        get() = _mockCaveList
}