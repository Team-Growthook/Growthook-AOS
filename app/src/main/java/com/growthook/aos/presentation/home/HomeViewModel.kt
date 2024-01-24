package com.growthook.aos.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.domain.usecase.DeleteSeedUseCase
import com.growthook.aos.domain.usecase.GetCavesUseCase
import com.growthook.aos.domain.usecase.GetGatherdThookUseCase
import com.growthook.aos.domain.usecase.ScrapSeedUseCase
import com.growthook.aos.domain.usecase.UnLockSeedUseCase
import com.growthook.aos.domain.usecase.actionplan.GetActionplanPercentUseCase
import com.growthook.aos.domain.usecase.actionplan.GetDoingActionplansUseCase
import com.growthook.aos.domain.usecase.home.GetSeedAlarmUseCase
import com.growthook.aos.domain.usecase.home.GetSeedsUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import com.growthook.aos.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val unLockSeedUseCase: UnLockSeedUseCase,
    private val getGatherdThookUseCase: GetGatherdThookUseCase,
    private val getGetActionplanPercentUseCase: GetActionplanPercentUseCase,
    private val getDoingActionplansUseCase: GetDoingActionplansUseCase,
) : ViewModel() {
    private val _doingActionplans = MutableStateFlow<List<ActionlistDetail>>(mutableListOf())
    val doingActionplans: MutableStateFlow<List<ActionlistDetail>> = _doingActionplans

    private val _nickName = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickName

    private val _alertAmount = MutableLiveData<Int>()
    val alertAmount: LiveData<Int> = _alertAmount

    private val _caves = MutableLiveData<List<Cave>>()
    val caves: LiveData<List<Cave>> = _caves

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    private val _isScrapedSuccess = MutableLiveData<Event<Boolean>>()
    val isScrapedSuccess: LiveData<Event<Boolean>> = _isScrapedSuccess

    private val _isUnlock = MutableLiveData<Boolean>()
    val isUnlock: LiveData<Boolean> = _isUnlock

    private val _gatherdThook = MutableLiveData<Int>()
    val gatherdThook: LiveData<Int> = _gatherdThook

    private val _scrapedInsights = MutableLiveData<List<Insight>>()
    val scrapedInsights: LiveData<List<Insight>> = _scrapedInsights

    private val _unScrapedInsights = MutableLiveData<List<Insight>>()
    val unScrapedInsights: LiveData<List<Insight>> = _unScrapedInsights

    private val memberId = MutableLiveData<Int>(0)

    val isMenuDismissed = MutableLiveData<Boolean>()

    val longClickInsight = MutableLiveData<Insight>()

    private val _actionplanPercent = MutableLiveData<Int>()
    val actionplanPercent: LiveData<Int> = _actionplanPercent

    init {
        viewModelScope.launch {
            memberId.value = getUserUseCase.invoke().memberId ?: 0
        }

        getAlertCount()
        getInsights()
        setNickName()
        getCaves()
        getGatherdThook()
        getActionplanPercent()
        getDoingActionplans()
    }

    fun getDoingActionplans() {
        viewModelScope.launch {
            getDoingActionplansUseCase.invoke(memberId.value ?: 0).onSuccess {
                _doingActionplans.value = it
            }.onFailure { throwable ->
                Timber.e(throwable.message)
            }
        }
    }

    fun getAlertCount() {
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
                _unScrapedInsights.value = insights
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }

    fun getScrapedInsight() {
        viewModelScope.launch {
            getSeedsUseCase.invoke(memberId.value ?: 0).onSuccess { insights ->
                _scrapedInsights.value = insights.filter { it.isScraped }
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }

    fun getCaves() {
        viewModelScope.launch {
            Log.d("user", "memberID:: ${getUserUseCase.invoke().memberId}")
            getCavesUseCase(memberId.value ?: 0).onSuccess { caves ->
                _caves.value = caves
            }
        }
    }

    fun setNickName() {
        viewModelScope.launch {
            _nickName.value = getUserUseCase.invoke().name ?: ""
        }
    }

    fun changeScrap(seedId: Int) {
        viewModelScope.launch {
            scrapSeedUseCase.invoke(seedId).onSuccess {
                _isScrapedSuccess.value = Event(true)
            }.onFailure {
                _isScrapedSuccess.value = Event(false)
            }
        }
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

    fun unLockSeed(seedId: Int) {
        viewModelScope.launch {
            unLockSeedUseCase.invoke(seedId).onSuccess {
                _isUnlock.value = true
            }.onFailure {
                _isUnlock.value = false
            }
        }
    }

    fun getGatherdThook() {
        viewModelScope.launch {
            getGatherdThookUseCase.invoke(memberId.value ?: 0).onSuccess { thookCount ->
                _gatherdThook.value = thookCount
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }

    fun getActionplanPercent() {
        viewModelScope.launch {
            getGetActionplanPercentUseCase.invoke(memberId.value ?: 0).onSuccess {
                _actionplanPercent.value = it
            }.onFailure { throwable ->
                Timber.e(throwable.message)
            }
        }
    }
}
