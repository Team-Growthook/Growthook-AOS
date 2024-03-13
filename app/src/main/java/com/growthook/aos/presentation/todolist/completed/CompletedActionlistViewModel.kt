package com.growthook.aos.presentation.todolist.completed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.domain.usecase.actionplan.GetFinishedActionplansUseCase
import com.growthook.aos.domain.usecase.actionplan.ScrapActionplanUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedActionlistViewModel @Inject constructor(
    private val getFinishedActionplansUseCase: GetFinishedActionplansUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val scrapActionplanUseCase: ScrapActionplanUseCase,
) : ViewModel() {
    private val _finishedActionplans = MutableStateFlow<List<ActionlistDetail>>(mutableListOf())
    val finishedActionplans: MutableStateFlow<List<ActionlistDetail>> = _finishedActionplans

    private val _event = MutableStateFlow<Event>(Event.Default)
    val event: MutableStateFlow<Event> = _event

    fun changeActionplanScrap(actionplanId: Int) {
        viewModelScope.launch {
            scrapActionplanUseCase.invoke(actionplanId).onSuccess {
                _event.value = Event.ScrapSuccess
            }.onFailure {
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
