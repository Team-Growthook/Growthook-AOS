package com.growthook.aos.presentation.actionlist.proceeding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.domain.usecase.actionplan.CompleteActionplanUseCase
import com.growthook.aos.domain.usecase.actionplan.GetDoingActionplansUseCase
import com.growthook.aos.domain.usecase.review.PostReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProceedingActionlistViewModel @Inject constructor(
    private val getDoingActionplansUseCase: GetDoingActionplansUseCase,
    private val completeActionplanUseCase: CompleteActionplanUseCase,
    private val postReviewUseCase: PostReviewUseCase,
) : ViewModel() {
    private val _doingActionplans = MutableStateFlow<List<ActionlistDetail>>(mutableListOf())
    val doingActionplans: MutableStateFlow<List<ActionlistDetail>> = _doingActionplans

    private val _actionplanId = MutableStateFlow(-1)
    val actionplanId: MutableStateFlow<Int> = _actionplanId

    private val _isComplete = MutableLiveData<Boolean>()
    val isComplete: MutableLiveData<Boolean> = _isComplete

    private val _event = MutableStateFlow<Event>(Event.Default)
    val event: MutableStateFlow<Event> = _event

    init {
        getDoingActionplans()
    }

    private fun getDoingActionplans() {
        viewModelScope.launch {
            getDoingActionplansUseCase.invoke(MEMBER_ID).onSuccess {
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
        object PostCompletedActionplanSuccess : Event
        object Failed : Event
    }

    companion object {
        private const val MEMBER_ID = 4
    }
}
