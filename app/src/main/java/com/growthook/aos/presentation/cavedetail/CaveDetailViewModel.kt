package com.growthook.aos.presentation.cavedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.CaveDetail
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.domain.usecase.DeleteSeedUseCase
import com.growthook.aos.domain.usecase.ScrapSeedUseCase
import com.growthook.aos.domain.usecase.cavedetail.DeleteCaveUseCase
import com.growthook.aos.domain.usecase.cavedetail.GetCaveDetailUseCase
import com.growthook.aos.domain.usecase.cavedetail.GetCaveSeedsUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
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
) : ViewModel() {
    private val _insights = MutableLiveData<List<Insight>>()
    val insights: LiveData<List<Insight>> = _insights

    private val scrapedInsight = MutableLiveData<List<Insight>>()

    private val _nickName = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickName

    private val _isSeedDelete = MutableLiveData<Boolean>()
    val isSeedDelete: LiveData<Boolean> = _isSeedDelete

    private val _isCaveDelete = MutableLiveData<Boolean>()
    val isCaveDelete: LiveData<Boolean> = _isCaveDelete

    private val _caveDetail = MutableLiveData<CaveDetail>()
    val caveDetail: LiveData<CaveDetail> = _caveDetail

    private val _isScrapedSuccess = MutableLiveData<Boolean>()
    val isScrapedSuccess: LiveData<Boolean> = _isScrapedSuccess

    private val memberId = MutableLiveData<Int>(0)

    val caveId = MutableStateFlow<Int>(0)

    val isMenuDismissed = MutableLiveData<Boolean>()

    val longClickInsight = MutableLiveData<Insight>()

    init {
        viewModelScope.launch {
            _nickName.value = getUserUseCase.invoke().name ?: ""
            memberId.value = 4
        }
    }

    fun getInsights() {
        viewModelScope.launch {
            getCaveSeedsUseCase.invoke(caveId.value).onSuccess { insights ->
                _insights.value = insights
                scrapedInsight.value = insights.filter { it.isScraped }
            }
        }
    }

    fun changeScrap(seedId: Int) {
        viewModelScope.launch {
            scrapSeedUseCase.invoke(seedId).onSuccess {
                _isScrapedSuccess.value = true
            }.onFailure {
                _isScrapedSuccess.value = false
            }
        }
    }

    fun getScrapedInsight() {
        _insights.value = scrapedInsight.value
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
}
