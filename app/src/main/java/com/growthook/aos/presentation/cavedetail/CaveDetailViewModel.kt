package com.growthook.aos.presentation.cavedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.CaveDetail
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.domain.usecase.DeleteSeedUseCase
import com.growthook.aos.domain.usecase.ScrapSeedUseCase
import com.growthook.aos.domain.usecase.UnLockSeedUseCase
import com.growthook.aos.domain.usecase.cavedetail.DeleteCaveUseCase
import com.growthook.aos.domain.usecase.cavedetail.GetCaveDetailUseCase
import com.growthook.aos.domain.usecase.cavedetail.GetCaveSeedsUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import com.growthook.aos.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CaveDetailViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val deleteCaveUseCase: DeleteCaveUseCase,
    private val deleteSeedUseCase: DeleteSeedUseCase,
    private val getCaveDetailUseCase: GetCaveDetailUseCase,
    private val getCaveSeedsUseCase: GetCaveSeedsUseCase,
    private val scrapSeedUseCase: ScrapSeedUseCase,
    private val unLockSeedUseCase: UnLockSeedUseCase,
) : ViewModel() {

    private val _scrapedInsights = MutableLiveData<List<Insight>>()
    val scrapedInsights: LiveData<List<Insight>> = _scrapedInsights

    private val _unScrapedInsights = MutableLiveData<List<Insight>>()
    val unScrapedInsights: LiveData<List<Insight>> = _unScrapedInsights

    private val _nickName = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickName

    private val _isSeedDelete = MutableLiveData<Boolean>()
    val isSeedDelete: LiveData<Boolean> = _isSeedDelete

    private val _isCaveDelete = MutableLiveData<Boolean>()
    val isCaveDelete: LiveData<Boolean> = _isCaveDelete

    private val _caveDetail = MutableLiveData<CaveDetail>()
    val caveDetail: LiveData<CaveDetail> = _caveDetail

    private val _isScrapedSuccess = MutableLiveData<Event<Boolean>>()
    val isScrapedSuccess: LiveData<Event<Boolean>> = _isScrapedSuccess

    private val _isUnlock = MutableLiveData<Boolean>()
    val isUnlock: LiveData<Boolean> = _isUnlock

    private val memberId = MutableLiveData<Int>(0)

    val caveId = MutableStateFlow<Int>(0)

    val isMenuDismissed = MutableLiveData<Boolean>()

    val longClickInsight = MutableLiveData<Insight>()

    init {
        viewModelScope.launch {
            _nickName.value = getUserUseCase.invoke().name ?: ""
            memberId.value = 9
        }
        getInsights()
    }

    fun getInsights() {
        viewModelScope.launch {
            getCaveSeedsUseCase.invoke(memberId.value ?: 0).onSuccess { insights ->
                _unScrapedInsights.value = insights
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }

    fun getScrapedInsights() {
        viewModelScope.launch {
            getCaveSeedsUseCase.invoke(memberId.value ?: 0).onSuccess { insights ->
                _scrapedInsights.value = insights.filter { it.isScraped }
            }.onFailure {
                Timber.e(it.message)
            }
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

    fun deleteCave(caveId: Int) {
        viewModelScope.launch {
            deleteCaveUseCase.invoke(caveId).onSuccess {
                _isCaveDelete.value = true
            }.onFailure {
                _isCaveDelete.value = false
            }
        }
    }

    fun getCaveDetail(caveId: Int) {
        viewModelScope.launch {
            getCaveDetailUseCase(memberId.value ?: 0, caveId).onSuccess {
                _caveDetail.value = it
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }

    fun deleteSeed(caveId: Int) {
        viewModelScope.launch {
            deleteSeedUseCase.invoke(caveId).onSuccess {
                _isSeedDelete.value = true
            }.onFailure {
                _isSeedDelete.value = false
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
}
