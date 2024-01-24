package com.growthook.aos.presentation.actionlist.proceeding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.domain.usecase.actionplan.CompleteActionplanUseCase
import com.growthook.aos.domain.usecase.actionplan.GetDoingActionplansUseCase
import com.growthook.aos.domain.usecase.actionplan.ScrapActionplanUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import com.growthook.aos.domain.usecase.review.PostReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProceedingActionlistViewModel @Inject constructor(
    private val getDoingActionplansUseCase: GetDoingActionplansUseCase,
    private val completeActionplanUseCase: CompleteActionplanUseCase,
    private val postReviewUseCase: PostReviewUseCase,
    private val scrapActionplanUseCase: ScrapActionplanUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {
    private val _doingActionplans = MutableStateFlow<List<ActionlistDetail>>(mutableListOf())
    val doingActionplans: MutableStateFlow<List<ActionlistDetail>> = _doingActionplans

    private val _actionplanId = MutableStateFlow(-1)
    val actionplanId: MutableStateFlow<Int> = _actionplanId

    private val _isComplete = MutableLiveData<Boolean>()
    val isComplete: MutableLiveData<Boolean> = _isComplete

    private val _event = MutableStateFlow<Event>(Event.Default)
    val event: MutableStateFlow<Event> = _event

    private val memberId = MutableLiveData<Int>(0)

    private val _scrapedActionplans = MutableStateFlow<List<ActionlistDetail>>(mutableListOf())
    val scrapedActionplans: MutableStateFlow<List<ActionlistDetail>> = _scrapedActionplans

    init {
        viewModelScope.launch {
            memberId.value = getUserUseCase.invoke().memberId ?: 0
        }
        getDoingActionplans()
    }

    fun changeActionplanScrap(actionplanId: Int) {
        viewModelScope.launch {
            scrapActionplanUseCase.invoke(actionplanId).onSuccess {
                _event.value = Event.ScrapSuccess
            }.onFailure {
                _event.value = Event.Failed
            }
        }
    }

    fun getScrapedActionplan() {
        viewModelScope.launch {
            getDoingActionplansUseCase.invoke(memberId.value ?: 0).onSuccess {
                _event.value = Event.GetScrapedActionplanSuccess
                _scrapedActionplans.value = it.filter { actionplan ->
                    actionplan.isScraped
                }
                _doingActionplans.value = _scrapedActionplans.value
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                _event.value = Event.Failed
            }
        }
    }

    fun getDoingActionplans() {
        viewModelScope.launch {
            getDoingActionplansUseCase.invoke(memberId.value ?: 0).onSuccess {
                _doingActionplans.value = it
                _event.value = Event.GetDoingActionplanSuccess
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                _event.value = Event.Failed
            }
        }
    }

    fun completeActionplan(actionplanId: Int) {
        viewModelScope.launch {
            completeActionplanUseCase.invoke(actionplanId).onSuccess {
                _event.value = Event.PostCompletedActionplanSuccess
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                _event.value = Event.Failed
            }
        }
    }

    fun postReview(actionplanId: Int, content: String) {
        viewModelScope.launch {
            postReviewUseCase.invoke(actionplanId, content).onSuccess {
                _event.value = Event.PostReviewSuccess
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                _event.value = Event.Failed
            }
        }
    }

    sealed interface Event {
        object Default : Event
        object PostReviewSuccess : Event
        object GetDoingActionplanSuccess : Event
        object GetScrapedActionplanSuccess : Event

        object PostCompletedActionplanSuccess : Event
        object ScrapSuccess : Event

        object Failed : Event
    }
}
