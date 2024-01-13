package com.growthook.aos.presentation.actionlist.proceeding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.domain.usecase.actionplan.GetDoingActionplansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProceedingActionlistViewModel @Inject constructor(
    private val getDoingActionplansUseCase: GetDoingActionplansUseCase,
) : ViewModel() {
    var doingActionplans: MutableStateFlow<List<ActionlistDetail>> =
        MutableStateFlow<List<ActionlistDetail>>(mutableListOf())

    init {
        getDoingActionplans()
    }

    private fun getDoingActionplans() {
        viewModelScope.launch {
            getDoingActionplansUseCase.invoke(MEMBER_ID).onSuccess {
                doingActionplans.value = it
            }
        }
    }

    companion object {
        private const val MEMBER_ID = 4
    }
}
