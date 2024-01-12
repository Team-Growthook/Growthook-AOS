package com.growthook.aos.presentation.insight.actionplan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Actionplan
import com.growthook.aos.domain.entity.Seed
import com.growthook.aos.domain.usecase.actionplan.GetActionplansUseCase
import com.growthook.aos.domain.usecase.actionplan.PostActionplansUseCase
import com.growthook.aos.domain.usecase.seeddetail.GetSeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ActionplanInsightViewModel @Inject constructor(
    private val getSeedUseCase: GetSeedUseCase,
    private val getActionplansUseCase: GetActionplansUseCase,
    private val postActionplanUseCase: PostActionplansUseCase,
) : ViewModel() {
    private val _actionplans = MutableStateFlow<List<Actionplan>>(listOf())
    val actionplans: MutableStateFlow<List<Actionplan>> = _actionplans

    private val _actionplan = MutableLiveData<Actionplan>()
    val actionplan: LiveData<Actionplan> = _actionplan

    private val _seedData = MutableLiveData<Seed>()
    val seedData: LiveData<Seed> = _seedData

    private val _event = MutableStateFlow<Event>(Event.Default)
    val event: MutableStateFlow<Event> = _event

    init {
        // TODO intent로 받은 seedId로 변경
        getSeedDetail(DUMMY_SEED)
        getActionplans(DUMMY_SEED)
    }

    fun postActionplan(seedId: Int, actionplan: String) {
        viewModelScope.launch {
            postActionplanUseCase.invoke(seedId, listOf(actionplan)).onSuccess {
                getActionplans(seedId)
                _event.value = Event.PostSuccess
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                _event.value = Event.PostFailed
            }
        }
    }

    private fun getSeedDetail(seedId: Int) {
        viewModelScope.launch {
            getSeedUseCase.invoke(seedId)
                .onSuccess { seed ->
                    Log.d("seed", "seed:: $seed")
                    _seedData.value = seed
                    _event.value = Event.Success
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                    _event.value = Event.Failed
                }
        }
    }

    fun getActionplans(seedId: Int) {
        viewModelScope.launch {
            getActionplansUseCase.invoke(seedId)
                .onSuccess { actionplan ->
                    _actionplans.value = actionplan
                    _event.value = Event.Success
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                    _event.value = Event.Failed
                }
        }
    }

    sealed interface Event {
        object Failed : Event
        object Success : Event
        object PostFailed : Event
        object PostSuccess : Event
        object Default : Event
    }

//    fun deleteActionplan(position: Int) {
//        val currentList = actionplanList.value.orEmpty().toMutableList()
//        if (position in currentList.indices) {
//            currentList.removeAt(position)
//            actionplanList.value = currentList.toList()
//        }
//    }

    companion object {
        const val DUMMY_SEED = 113
    }
}
