package com.growthook.aos.presentation.insight.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.seeddetail.PostSeedUseCase
import com.growthook.aos.util.extension.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InsightWriteViewModel @Inject constructor(
    private val postSeedUseCase: PostSeedUseCase
) : ViewModel() {

    private val _insight: MutableLiveData<String> = MutableLiveData()
    val insight: LiveData<String>
        get() = _insight

    private val _memo: MutableLiveData<String> = MutableLiveData("")
    val memo: LiveData<String>
        get() = _memo

    private val _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String>
        get() = _url

    private val _urlChoice: MutableLiveData<String> = MutableLiveData("")
    val urlChoice: LiveData<String>
        get() = _urlChoice

    val selectedCaveId = MutableLiveData<Int>()

    private val _selectedGoalMonth: MutableLiveData<Int> = MutableLiveData()
    val selectedGoalMonth: LiveData<Int>
        get() = _selectedGoalMonth

    private val _postSeedSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val postSeedSuccess: LiveData<Boolean>
        get() = _postSeedSuccess

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
        addSourceList(
            insight,
            selectedCaveId,
            url,
            selectedGoalMonth
        ) { checkInsightWriteEnabled() }
    }

    private fun checkInsightWriteEnabled(): Boolean =
        !insight.value.isNullOrBlank()
                && !(selectedCaveId.value == null)
                && !url.value.isNullOrBlank()
                && !(selectedGoalMonth.value == null)

    fun postNewSeed() {
        viewModelScope.launch {
            postSeedUseCase(
                selectedCaveId.value ?: 44,
                insight.value.toString(),
                memo.value.toString(),
                url.value.toString(),
                urlChoice.value.toString(),
                selectedGoalMonth.value ?: 1
            ).onSuccess {
                _postSeedSuccess.value = true
            }.onFailure {
                _postSeedSuccess.value = false
                Timber.d("InsightWrite: ${it.message}")
            }
        }
    }
}