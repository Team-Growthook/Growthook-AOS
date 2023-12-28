package com.growthook.aos.presentation.insight.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.util.extension.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InsightWriteViewModel @Inject constructor() : ViewModel() {

    private val _insight: MutableLiveData<String> = MutableLiveData()
    val insight: LiveData<String>
        get() = _insight

    private val _memo: MutableLiveData<String> = MutableLiveData()
    val memo: LiveData<String>
        get() = _memo

    private val _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String>
        get() = _url

    private val _urlChoice: MutableLiveData<String> = MutableLiveData()
    val urlChoice: LiveData<String>
        get() = _urlChoice

    private val _selectedCaveName: MutableLiveData<String> = MutableLiveData()
    val selectedCaveName: LiveData<String>
        get() = _selectedCaveName

    private val _selectedGoalMonth: MutableLiveData<Int> = MutableLiveData()
    val selectedGoalMonth: LiveData<Int>
        get() = _selectedGoalMonth

    fun getInsight(insight: String) {
        _insight.value = insight
    }

    fun getMemo(memo: String) {
        _memo.value = memo
    }

    fun getUrl(url: String) {
        _url.value = url
    }

    fun getUrlChoice(urlChoice: String) {
        _urlChoice.value = urlChoice
    }

    fun setSelectedCaveName(caveName: String) {
        _selectedCaveName.value = caveName
    }

    fun setSelectedGoalMonth(goalMonth: Int) {
        _selectedGoalMonth.value = goalMonth
    }

    val checkInsightWriteBtnEnabled = MediatorLiveData<Boolean>().apply {
        addSourceList(insight, selectedCaveName, url, selectedGoalMonth) { checkInsightWriteEnabled() }
    }

    private fun checkInsightWriteEnabled(): Boolean =
        !insight.value.isNullOrBlank()
                && !selectedCaveName.value.isNullOrBlank()
                && !url.value.isNullOrBlank()
                && !(selectedGoalMonth.value == null)

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