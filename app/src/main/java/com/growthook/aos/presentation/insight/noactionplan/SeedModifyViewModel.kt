package com.growthook.aos.presentation.insight.noactionplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growthook.aos.domain.entity.SeedInfo
import com.growthook.aos.util.extension.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeedModifyViewModel @Inject constructor(): ViewModel() {

    private val _insightModify: MutableLiveData<String> = MutableLiveData()
    val insightModify: LiveData<String>
        get() = _insightModify

    private val _memoModify: MutableLiveData<String> = MutableLiveData()
    val memoModify: LiveData<String>
        get() = _memoModify

    private val _sourceModify: MutableLiveData<String> = MutableLiveData()
    val sourceModify: LiveData<String>
        get() = _sourceModify

    private val _urlModify: MutableLiveData<String> = MutableLiveData()
    val urlModify: LiveData<String>
        get() = _urlModify

    fun setInsightModify(insight: String) {
        _insightModify.value = insight
    }

    fun setMemoModify(memo: String) {
        _memoModify.value = memo
    }

    fun setSourceModify(source: String) {
        _sourceModify.value = source
    }

    fun setUrlModify(url: String) {
        _urlModify.value = url
    }

    val checkSeedModifyBtnEnabled = MediatorLiveData<Boolean>().apply {
        addSourceList(insightModify, memoModify, sourceModify, urlModify) { checkSeedModifyEnabled() }
    }

    private fun checkSeedModifyEnabled(): Boolean =
        insightModify.value != _seedInfo.value?.insight
                || memoModify.value != _seedInfo.value?.memo
                || sourceModify.value != _seedInfo.value?.source
                || urlModify.value != _seedInfo.value?.url

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