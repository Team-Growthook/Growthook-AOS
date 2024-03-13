package com.growthook.aos.presentation.todolist.completed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.actionplan.ScrapActionplanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedActionlistViewModel @Inject constructor(
    private val scrapActionplanUseCase: ScrapActionplanUseCase,
) : ViewModel() {
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
