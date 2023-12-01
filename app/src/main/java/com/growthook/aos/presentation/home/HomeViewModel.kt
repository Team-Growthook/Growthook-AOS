package com.growthook.aos.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _nickName = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickName

    private val _insightAmount = MutableLiveData(0)
    val insightAmount: LiveData<Int> = _insightAmount

    private val _alertAmount = MutableLiveData<Int>()
    val alertAmount: LiveData<Int> = _alertAmount

    private val _insights = MutableLiveData<List<Insight>>()
    val insights: LiveData<List<Insight>> = _insights

    private val _caves = MutableLiveData<List<Cave>>()
    val caves: LiveData<List<Cave>> = _caves

    val isMenuDismissed = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            getUserUseCase.invoke().name.let { nickName ->
                _nickName.value = nickName
            }
            getAlertCount()
            getCaves()
            getInsights()
        }
    }

    private fun getAlertCount() {
        _alertAmount.value = 0
    }

    fun getInsights() {
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
    }

    fun getScrapedInsight() {
        _insights.value = _insights.value?.filter { it.isScraped }
        Timber.d("getScrapedInsight ${_insights.value?.size}")
    }

    fun getCaves() {
        val dummyCave = listOf(
            Cave(1, "연습용"),
            Cave(2, "연습연습연습연습"),
            Cave(3, "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ"),
            Cave(4, "동굴"),
            Cave(5, "연습용"),
            Cave(6, "연습연습연습연습"),
            Cave(7, "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ"),
            Cave(8, "동굴"),
        )

        _caves.value = dummyCave
    }

    fun changeScrap(isScrap: Boolean) {
        // TODO 스크랩 api 구현
    }
}
