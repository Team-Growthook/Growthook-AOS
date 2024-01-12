package com.growthook.aos.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.domain.usecase.DeleteSeedUseCase
import com.growthook.aos.domain.usecase.GetCavesUseCase
import com.growthook.aos.domain.usecase.ScrapSeedUseCase
import com.growthook.aos.domain.usecase.home.GetSeedAlarmUseCase
import com.growthook.aos.domain.usecase.home.GetSeedsUseCase
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
    private val getSeedAlarmUseCase: GetSeedAlarmUseCase,
    private val getSeedsUseCase: GetSeedsUseCase,
    private val scrapSeedUseCase: ScrapSeedUseCase,
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

    private val scrapedInsight = MutableLiveData<List<Insight>>()

    private val memberId = MutableLiveData<Int>(0)

    val isMenuDismissed = MutableLiveData<Boolean>()

    val longClickInsight = MutableLiveData<Insight>()

    init {
        viewModelScope.launch {

            getAlertCount()
            getInsights()
            setNickName()
            getCaves()
            memberId.value = getUserUseCase.invoke().memberId ?: 0
        }
    }

    private fun getAlertCount() {
        viewModelScope.launch {
            getSeedAlarmUseCase.invoke(memberId.value ?: 0).onSuccess { seedCount ->
                _alertAmount.value = seedCount
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }

    fun getInsights() {
        viewModelScope.launch {
            getSeedsUseCase.invoke(memberId.value ?: 0).onSuccess { insights ->
                _insights.value = insights
                scrapedInsight.value = insights.filter { it.isScraped }
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }

    fun getScrapedInsight() {
        _insights.value = scrapedInsight.value
        Timber.d("getScrapedInsight ${_insights.value?.size}")
    }

    fun getCaves() {
        viewModelScope.launch {
            Log.d("user", "memberID:: ${getUserUseCase.invoke().memberId}")
            getCavesUseCase(DUMMY_MEMBER_ID).onSuccess { caves ->
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

    companion object {
        const val DUMMY_MEMBER_ID = 3
    }
}
