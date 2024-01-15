package com.growthook.aos.presentation.insight.actionplan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Actionplan
import com.growthook.aos.domain.entity.Seed
import com.growthook.aos.domain.usecase.ScrapSeedUseCase
import com.growthook.aos.domain.usecase.actionplan.CompleteActionplanUseCase
import com.growthook.aos.domain.usecase.actionplan.DeleteActionplanUseCase
import com.growthook.aos.domain.usecase.actionplan.GetActionplansUseCase
import com.growthook.aos.domain.usecase.actionplan.ModifyActionplanUseCase
import com.growthook.aos.domain.usecase.actionplan.PostActionplansUseCase
import com.growthook.aos.domain.usecase.review.PostReviewUseCase
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
    private val completeActionplanUseCase: CompleteActionplanUseCase,
    private val modifyActionplanUseCase: ModifyActionplanUseCase,
    private val deleteActionplanUseCase: DeleteActionplanUseCase,
    private val postReviewUseCase: PostReviewUseCase,
    private val scrapSeedUseCase: ScrapSeedUseCase,
) : ViewModel() {
    var seedId: Int = 0

    private val _actionplans = MutableStateFlow<List<Actionplan>>(listOf())
    val actionplans: MutableStateFlow<List<Actionplan>> = _actionplans

    private val _actionplan = MutableLiveData<Actionplan>()
    val actionplan: LiveData<Actionplan> = _actionplan

    private val _seedData = MutableLiveData<Seed>()
    val seedData: LiveData<Seed> = _seedData

    private val _event = MutableStateFlow<Event>(Event.Default)
    val event: MutableStateFlow<Event> = _event

    fun postActionplan(seedId: Int, actionplan: String) {
        viewModelScope.launch {
            postActionplanUseCase.invoke(seedId, listOf(actionplan)).onSuccess {
                getActionplans()
                _event.value = Event.PostActionplanSuccess
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                _event.value = Event.Failed
            }
        }
    }

    fun getSeedDetail() {
        viewModelScope.launch {
            getSeedUseCase.invoke(seedId)
                .onSuccess { seed ->
                    Log.d("seed", "seed:: $seed")
                    _seedData.value = seed
                    _event.value = Event.GetSeedSuccess
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                    _event.value = Event.Failed
                }
        }
    }

    fun getActionplans() {
        viewModelScope.launch {
            getActionplansUseCase.invoke(seedId)
                .onSuccess { actionplan ->
                    _actionplans.value = actionplan
                    _event.value = Event.GetActionplanSuccess
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                    _event.value = Event.Failed
                }
        }
    }

    fun completeActionplan(actionplanId: Int) {
        viewModelScope.launch {
            completeActionplanUseCase.invoke(actionplanId).onSuccess {
                getActionplans()
                _event.value = Event.PostCompletedActionplanSuccess
            }.onFailure {
                Timber.e(it.message)
                _event.value = Event.Failed
            }
        }
    }

    fun modifyActionplan(actionplanId: Int, content: String) {
        viewModelScope.launch {
            modifyActionplanUseCase.invoke(actionplanId, content)
                .onSuccess {
                    getActionplans()
                    _event.value = Event.ModifySuccess
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                    _event.value = Event.Failed
                }
        }
    }

    fun deleteActionplan(actionplanId: Int) {
        viewModelScope.launch {
            deleteActionplanUseCase.invoke(actionplanId)
                .onSuccess {
                    getActionplans()
                    _event.value = Event.DeleteSuccess
                }
                .onFailure { throwable ->
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

    fun changeScrap() {
        viewModelScope.launch {
//            scrapSeedUseCase.invoke(seedId).onSuccess {
//                _event.value = Event.ScrapSuccess
//            }.onFailure {
//                _event.value = Event.Failed
//            }
            // TODO 액션플랜 스크랩
        }
    }

    sealed interface Event {
        object Failed : Event
        object GetSeedSuccess : Event
        object GetActionplanSuccess : Event
        object PostActionplanSuccess : Event
        object PostCompletedActionplanSuccess : Event
        object ScrapSuccess : Event

        object Default : Event
        object ModifySuccess : Event
        object DeleteSuccess : Event
        object PostReviewSuccess : Event
    }

    companion object {
        const val DUMMY_SEED = 113
    }
}
