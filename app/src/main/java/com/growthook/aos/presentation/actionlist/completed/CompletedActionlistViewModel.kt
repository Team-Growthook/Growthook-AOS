package com.growthook.aos.presentation.actionlist.completed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.domain.usecase.actionplan.GetFinishedActionplansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedActionlistViewModel @Inject constructor(
    private val getFinishedActionplansUseCase: GetFinishedActionplansUseCase,
) : ViewModel() {
    private val _finishedActionplans = MutableStateFlow<List<ActionlistDetail>>(mutableListOf())
    val finishedActionplans: MutableStateFlow<List<ActionlistDetail>> = _finishedActionplans

    init {
        getFinishedActionplans()
    }

    private fun getFinishedActionplans() {
        viewModelScope.launch {
            getFinishedActionplansUseCase.invoke(MEMBER_ID).onSuccess {
                finishedActionplans.value = it
            }
        }
    }

    companion object {
        private const val MEMBER_ID = 4
    }
}
