package com.growthook.aos.presentation.cavedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.CaveDetail
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.domain.usecase.cavedetail.DeleteCaveUseCase
import com.growthook.aos.domain.usecase.cavedetail.GetCaveDetailUseCase
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
    private val getCaveDetailUseCase: GetCaveDetailUseCase,
) : ViewModel() {
    private val _insights = MutableLiveData<List<Insight>>()
    val insights: LiveData<List<Insight>> = _insights

    private val scrapedInsight = MutableLiveData<List<Insight>>()

    private val _nickName = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickName

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    private val _caveDetail = MutableLiveData<CaveDetail>()
    val caveDetail: LiveData<CaveDetail> = _caveDetail

    val caveId = MutableStateFlow<Int>(0)

    val isMenuDismissed = MutableLiveData<Boolean>()

    val longClickInsight = MutableLiveData<Insight>()

    init {
        viewModelScope.launch {
            getUserUseCase.invoke().name.let { nickName ->
                _nickName.value = nickName
            }
        }
    }

    fun getInsights(caveId: Int) {
        var dummyInsights = listOf(
            Insight(
                "제목1",
                12,
                isScraped = true,
                isLocked = false,
                isAction = false,
                1,
            ),
            Insight(
                "제목2",
                24,
                isScraped = false,
                isLocked = false,
                isAction = false,
                2,
            ),
            Insight(
                "제목3",
                12,
                isScraped = true,
                isLocked = true,
                isAction = false,
                3,
            ),
            Insight(
                "제목4",
                12,
                isScraped = true,
                isLocked = false,
                isAction = false,
                4,
            ),
            Insight(
                "제목5",
                12,
                isScraped = false,
                isLocked = true,
                isAction = false,
                5,
            ),
            Insight(
                "제목6",
                12,
                isScraped = true,
                isLocked = false,
                isAction = false,
                6,
            ),
            Insight(
                "제목7",
                12,
                isScraped = false,
                isLocked = false,
                isAction = true,
                7,
            ),
            Insight(
                "제목8",
                12,
                isScraped = true,
                isLocked = false,
                isAction = false,
                8,
            ),
            Insight(
                "제목9",
                12,
                isScraped = true,
                isLocked = true,
                isAction = false,
                9,
            ),
            Insight(
                "제목10",
                12,
                isScraped = false,
                isLocked = false,
                isAction = true,
                10,
            ),

        )

        _insights.value = dummyInsights
        scrapedInsight.value = _insights.value?.filter { it.isScraped }
    }

    fun changeScrap(isScrap: Boolean) {
        // TODO 스크랩 api 구현
    }

    fun getScrapedInsight() {
        _insights.value = scrapedInsight.value
    }

    fun deleteCave(caveId: Int) {
        viewModelScope.launch {
            deleteCaveUseCase.invoke(caveId).onSuccess {
                _isDelete.value = true
            }.onFailure {
                _isDelete.value = false
            }
        }
    }

    fun getCaveDetail(caveId: Int) {
        viewModelScope.launch {
            getCaveDetailUseCase(getUserUseCase.invoke()?.memberId ?: 3, caveId).onSuccess {
                _caveDetail.value = it
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }
}
