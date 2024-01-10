package com.growthook.aos.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.domain.usecase.DeleteSeedUseCase
import com.growthook.aos.domain.usecase.GetCavesUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val deleteSeedUseCase: DeleteSeedUseCase,
    private val getCavesUseCase: GetCavesUseCase,
) : ViewModel() {

    private val _nickName = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickName

    private val _alertAmount = MutableLiveData<Int>()
    val alertAmount: LiveData<Int> = _alertAmount

    private val _insights = MutableLiveData<List<Insight>>()
    val insights: LiveData<List<Insight>> = _insights

    private val _caves = MutableLiveData<List<Cave>>()
    val caves: LiveData<List<Cave>> = _caves

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    val isMenuDismissed = MutableLiveData<Boolean>()

    val longClickInsight = MutableLiveData<Insight>()

    init {
        viewModelScope.launch {

            getAlertCount()
            getInsights()
            setNickName()
            getCaves()
        }
    }

    private fun getAlertCount() {
        _alertAmount.value = 0
    }

    fun getInsights() {

        // _insights.value = dummyInsights
    }

    fun getScrapedInsight() {
        _insights.value = _insights.value?.filter { it.isScraped }
        Timber.d("getScrapedInsight ${_insights.value?.size}")
    }

    fun getCaves() {
        viewModelScope.launch {
            getCavesUseCase(getUserUseCase.invoke().memberId ?: 0).onSuccess { caves ->
                _caves.value = caves
            }
        }
    }

    fun setNickName() {
        viewModelScope.launch {
            _nickName.value = getUserUseCase.invoke().name ?: ""
        }
    }

    fun changeScrap(isScrap: Boolean) {
        // TODO 스크랩 api 구현
    }

    fun deleteSeed(caveId: Int) {
        viewModelScope.launch {
            deleteSeedUseCase.invoke(caveId).onSuccess {
                _isDelete.value = true
            }.onFailure {
                _isDelete.value = false
            }
        }
    }
}
