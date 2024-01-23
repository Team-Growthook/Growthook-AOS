package com.growthook.aos.presentation.insight.write

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Seed
import com.growthook.aos.domain.usecase.seeddetail.GetSeedUseCase
import com.growthook.aos.presentation.insight.noactionplan.NoActionplanInsightViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InsightWriteNewViewModel @Inject constructor(
    private val getSeedUseCase: GetSeedUseCase,
) : ViewModel() {

    private val _newSeedData = MutableLiveData<Seed>()
    val newSeedData: LiveData<Seed> = _newSeedData

    fun getNewSeedData(seedId: Int) {
        viewModelScope.launch {
            getSeedUseCase.invoke(seedId)
                .onSuccess { seed ->
                    _newSeedData.value = seed
                }
                .onFailure { throwable ->
                    Timber.e("서버 통신 실패 -> ${throwable.message}")
                }
        }
    }
}