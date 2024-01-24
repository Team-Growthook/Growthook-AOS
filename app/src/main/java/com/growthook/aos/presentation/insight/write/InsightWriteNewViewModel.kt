package com.growthook.aos.presentation.insight.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Seed
import com.growthook.aos.domain.usecase.ScrapSeedUseCase
import com.growthook.aos.domain.usecase.seeddetail.GetSeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InsightWriteNewViewModel @Inject constructor(
    private val getSeedUseCase: GetSeedUseCase,
    private val scrapSeedUseCase: ScrapSeedUseCase,
) : ViewModel() {

    private val _newSeedData = MutableLiveData<Seed>()
    val newSeedData: LiveData<Seed> = _newSeedData

    private val _isScrapedSuccess = MutableLiveData<Boolean>()
    val isScrapedSuccess: LiveData<Boolean>
        get() = _isScrapedSuccess

    fun getNewSeedData(seedId: Int) {
        viewModelScope.launch {
            getSeedUseCase(seedId)
                .onSuccess { seed ->
                    _newSeedData.value = seed
                }
                .onFailure { throwable ->
                    Timber.e("서버 통신 실패 -> ${throwable.message}")
                }
        }
    }

    fun changeSeedScrap(seedId: Int) {
        viewModelScope.launch {
            scrapSeedUseCase.invoke(seedId).onSuccess {
                _isScrapedSuccess.value = true
            }.onFailure {
                _isScrapedSuccess.value = false
            }
        }
    }
}