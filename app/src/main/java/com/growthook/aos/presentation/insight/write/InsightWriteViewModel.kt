package com.growthook.aos.presentation.insight.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    val selectedCaveId = MutableLiveData<Int>()

    private val _selectedGoalMonth: MutableLiveData<Int> = MutableLiveData()
    val selectedGoalMonth: LiveData<Int>
        get() = _selectedGoalMonth

    fun setInsight(insight: String) {
        _insight.value = insight
    }

    fun setMemo(memo: String) {
        _memo.value = memo
    }

    fun setUrl(url: String) {
        _url.value = url
    }

    fun setUrlChoice(urlChoice: String) {
        _urlChoice.value = urlChoice
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
}