package com.growthook.aos.presentation.insight.noactionplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.SeedInfo
import com.growthook.aos.domain.usecase.MoveSeedUseCase
import com.growthook.aos.domain.usecase.seeddetail.ModifySeedUseCase
import com.growthook.aos.util.extension.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SeedModifyViewModel @Inject constructor(
    private val modifySeedUseCase: ModifySeedUseCase,
    private val moveSeedUseCase: MoveSeedUseCase,
) : ViewModel() {

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

    val selectedCaveId = MutableStateFlow<Int>(0)

    val checkSeedModifyBtnEnabled = MediatorLiveData<Boolean>().apply {
        addSourceList(
            insightModify,
            memoModify,
            sourceModify,
            urlModify,
        ) { checkSeedModifyEnabled() }
    }

    private val _seedInfo = MutableLiveData<SeedInfo>()
    val seedInfo: LiveData<SeedInfo>
        get() = _seedInfo

    private val seedModifyResponse = MutableLiveData<Boolean>()

    private val seedMoveResponse = MutableLiveData<Boolean>()

    val isModifySuccess = MediatorLiveData<Boolean>().apply {
        addSourceList(seedMoveResponse, seedModifyResponse) { checkIsModifySuccess() }
    }

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

    private fun checkSeedModifyEnabled(): Boolean =
        insightModify.value != seedInfo.value?.insight ||
            memoModify.value != seedInfo.value?.memo ||
            sourceModify.value != seedInfo.value?.source ||
            urlModify.value != seedInfo.value?.url

    private fun checkIsModifySuccess(): Boolean =
        seedModifyResponse.value == true && seedMoveResponse.value == true

    fun setSeedInfo(info: SeedInfo) {
        _seedInfo.value = info
    }

    fun modifySeed(insight: String, memo: String, source: String, url: String) {
        viewModelScope.launch {
            modifySeedUseCase(_seedInfo.value?.seedId?:0, insight, memo, source, url)
                .onSuccess {
                    seedModifyResponse.value = true
                    Timber.d("서버 통신 -> 성공 씨앗 수정 ")
                }.onFailure {
                    seedModifyResponse.value = false
                    Timber.d("서버 통신 -> 실패 ${it.message}")
                }
        }
    }

    fun moveSeed() {
        viewModelScope.launch {
            moveSeedUseCase.invoke(_seedInfo.value?.seedId?:0, selectedCaveId.value).onSuccess {
                seedMoveResponse.value = true
            }.onFailure {
                seedMoveResponse.value = false
            }
        }
    }
}
