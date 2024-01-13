package com.growthook.aos.presentation.actionlist.proceeding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.domain.usecase.actionplan.CompleteActionplanUseCase
import com.growthook.aos.domain.usecase.actionplan.GetDoingActionplansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProceedingActionlistViewModel @Inject constructor(
    private val getDoingActionplansUseCase: GetDoingActionplansUseCase,
    private val completeActionplanUseCase: CompleteActionplanUseCase,
) : ViewModel() {
    private val _doingActionplans = MutableStateFlow<List<ActionlistDetail>>(mutableListOf())
    val doingActionplans: MutableStateFlow<List<ActionlistDetail>> = _doingActionplans

    private val _actionplanId = MutableStateFlow(-1)
    val actionplanId: MutableStateFlow<Int> = _actionplanId

    private val _isComplete = MutableLiveData<Boolean>()
    val isComplete: MutableLiveData<Boolean> = _isComplete

    init {
        getDoingActionplans()
    }

    private fun getDoingActionplans() {
        viewModelScope.launch {
            getDoingActionplansUseCase.invoke(MEMBER_ID).onSuccess {
                _doingActionplans.value = it
            }
        }
    }

    fun completeActionplan(actionplanId: Int) {
        viewModelScope.launch {
            completeActionplanUseCase.invoke(actionplanId).onSuccess {
                _isComplete.value = true
            }.onFailure {
                _isComplete.value = false
            }
        }
    }

    companion object {
        private const val MEMBER_ID = 4
    }
}
