package com.growthook.aos.presentation.actionlist.completed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.domain.usecase.actionplan.GetFinishedActionplansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CompletedActionlistViewModel @Inject constructor(
    private val getFinishedActionplansUseCase: GetFinishedActionplansUseCase,
) : ViewModel() {
    private val _finishedActionplans = MutableStateFlow<List<ActionlistDetail>>(mutableListOf())
    val finishedActionplans: MutableStateFlow<List<ActionlistDetail>> = _finishedActionplans

    private val scrapedActionplans = MutableLiveData<List<ActionlistDetail>>()

    init {
        getFinishedActionplans()
    }

    fun getScrapedActionplan() {
        _finishedActionplans.value = scrapedActionplans.value.orEmpty()
        Timber.d("getScrapedActionplan() ${_finishedActionplans.value}")
    }

    fun getFinishedActionplans() {
        viewModelScope.launch {
            getFinishedActionplansUseCase.invoke(MEMBER_ID).onSuccess {
                finishedActionplans.value = it
                scrapedActionplans.value = it.filter { actionplan ->
                    actionplan.isScraped
                }
            }
        }
    }

    companion object {
        private const val MEMBER_ID = 4
    }
}
