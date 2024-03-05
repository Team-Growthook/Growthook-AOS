package com.growthook.aos.presentation.todolist.completed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.domain.usecase.actionplan.GetFinishedActionplansUseCase
import com.growthook.aos.domain.usecase.actionplan.ScrapActionplanUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CompletedActionlistViewModel @Inject constructor(
    private val getFinishedActionplansUseCase: GetFinishedActionplansUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val scrapActionplanUseCase: ScrapActionplanUseCase,
) : ViewModel() {
    private val _finishedActionplans = MutableStateFlow<List<ActionlistDetail>>(mutableListOf())
    val finishedActionplans: MutableStateFlow<List<ActionlistDetail>> = _finishedActionplans

    private val memberId = MutableLiveData<Int>(0)

    private val _event = MutableStateFlow<Event>(Event.Default)
    val event: MutableStateFlow<Event> = _event

    private val _scrapedActionplans = MutableStateFlow<List<ActionlistDetail>>(mutableListOf())
    val scrapedActionplans: MutableStateFlow<List<ActionlistDetail>> = _scrapedActionplans

    init {
        viewModelScope.launch {
            memberId.value = getUserUseCase.invoke().memberId ?: 0
        }
        getFinishedActionplans()
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
            getFinishedActionplansUseCase.invoke(memberId.value ?: 0).onSuccess {
                _scrapedActionplans.value = it.filter { actionplan ->
                    actionplan.isScraped
                }
                _finishedActionplans.value = _scrapedActionplans.value
                _event.value = Event.GetScrapedActionplanSuccess
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                _event.value = Event.Failed
            }
        }
    }

    fun getFinishedActionplans() {
        viewModelScope.launch {
            getFinishedActionplansUseCase.invoke(memberId.value ?: 0).onSuccess {
                _finishedActionplans.value = it
                _event.value = Event.GetFinishedActionplanSuccess
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                _event.value = Event.Failed
            }
        }
    }

    sealed interface Event {
        object Default : Event
        object GetFinishedActionplanSuccess : Event
        object GetScrapedActionplanSuccess : Event
        object ScrapSuccess : Event
        object Failed : Event
    }
}
