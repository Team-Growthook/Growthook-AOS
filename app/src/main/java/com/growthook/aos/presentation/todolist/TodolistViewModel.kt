package com.growthook.aos.presentation.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.domain.usecase.actionplan.GetActionplanPercentUseCase
import com.growthook.aos.domain.usecase.actionplan.GetDoingActionplansUseCase
import com.growthook.aos.domain.usecase.actionplan.GetFinishedActionplansUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import com.growthook.aos.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodolistViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getGetActionplanPercentUseCase: GetActionplanPercentUseCase,
    private val getDoingActionplansUseCase: GetDoingActionplansUseCase,
    private val getFinishedActionplansUseCase: GetFinishedActionplansUseCase,
) : BaseViewModel() {

    private val _doingActionplans = MutableStateFlow<List<ActionlistDetail>>(listOf())
    val doingActionplans: MutableStateFlow<List<ActionlistDetail>> = _doingActionplans

    private val _finishedActionplans = MutableStateFlow<List<ActionlistDetail>>(mutableListOf())
    val finishedActionplans: MutableStateFlow<List<ActionlistDetail>> = _finishedActionplans

    private val _actionplanPercent = MutableLiveData<Int>()
    val actionplanPercent: LiveData<Int> = _actionplanPercent

    private val _event = MutableStateFlow<Event>(Event.Empty)
    val event: MutableStateFlow<Event> = _event

    val memberId = MutableLiveData(0)

    val filterState = MutableLiveData(ALL)

    init {
        viewModelScope.launch {
            memberId.value = getUserUseCase.invoke().memberId ?: 0
        }
        getActionplanPercent()
    }

    fun getDoingActionplans() {
        viewModelScope.launch {
            getDoingActionplansUseCase.invoke(memberId.value ?: 0).collect { apiResult ->
                val result = receiveApiResult(apiResult)
                if (result != null) {
                    _doingActionplans.value = if (filterState.value == ALL) {
                        result.toDoingTodo()
                    } else {
                        result.toDoingTodo().filter { it.isScraped }
                    }
                } else {
                    _event.value = Event.FailedGetDoingTodo
                }
            }
        }
    }

    fun getFinishedActionplans() {
        viewModelScope.launch {
            getFinishedActionplansUseCase.invoke(memberId.value ?: 0).collect { apiResult ->
                val result = receiveApiResult(apiResult)
                if (result != null) {
                    _finishedActionplans.value = if (filterState.value == ALL) {
                        result.toDoneTodo()
                    } else {
                        result.toDoneTodo().filter { it.isScraped }
                    }
                } else {
                    _event.value = Event.FailedGetFinishedTodo
                }
            }
        }
    }

    fun getActionplanPercent() {
        viewModelScope.launch {
            getGetActionplanPercentUseCase.invoke(memberId.value ?: 0).onSuccess {
                _actionplanPercent.value = it
                _event.value = Event.FailedGetDoingTodo
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                _event.value = Event.FailedGetPercent
            }
        }
    }

    sealed interface Event {
        object Empty : Event
        object FailedGetDoingTodo : Event
        object FailedGetFinishedTodo : Event
        object FailedGetPercent : Event
    }

    companion object {
        const val SCRAPED = "scraped"
        const val ALL = "all"
    }
}
