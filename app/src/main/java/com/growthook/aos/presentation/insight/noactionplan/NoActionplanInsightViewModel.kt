package com.growthook.aos.presentation.insight.noactionplan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.domain.entity.Seed
import com.growthook.aos.domain.usecase.DeleteSeedUseCase
import com.growthook.aos.domain.usecase.GetCavesUseCase
import com.growthook.aos.domain.usecase.MoveSeedUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import com.growthook.aos.domain.usecase.seeddetail.GetSeedUseCase
import com.growthook.aos.domain.usecase.seeddetail.ModifySeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NoActionplanInsightViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getSeedUseCase: GetSeedUseCase,
    private val getCavesUseCase: GetCavesUseCase,
    private val deleteSeedUseCase: DeleteSeedUseCase,
    private val modifySeedUseCase: ModifySeedUseCase,
    private val moveSeedUseCase: MoveSeedUseCase,
) :
    ViewModel() {
    private val _caves = MutableLiveData<List<Cave>>()
    val caves: LiveData<List<Cave>> = _caves

    private val _seedData = MutableLiveData<Seed>()
    val seedData: LiveData<Seed> = _seedData

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    private val _isMove = MutableLiveData<Boolean>()
    val isMove: LiveData<Boolean> = _isMove

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    var seedId: Int = 0

    private val memberId = MutableLiveData<Int>(0)

    init {
        viewModelScope.launch {
            memberId.value = getUserUseCase.invoke().memberId ?: 0
        }
    }

    fun moveSeed(seedId: Int, caveId: Int) {
        viewModelScope.launch {
            moveSeedUseCase(seedId, caveId).onSuccess {
                _isMove.value = true
            }.onFailure {
                _isMove.value = false
                Timber.d("씨앗 옮기기 ${it.message}")
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

    fun getCaves() {
        viewModelScope.launch {
            viewModelScope.launch {
                getCavesUseCase(memberId.value ?: 0).onSuccess { caves ->
                    _caves.value = caves
                }
            }
        }
    }

    fun deleteSeed() {
        viewModelScope.launch {
            deleteSeedUseCase.invoke(seedId).onSuccess {
                _event.value = Event.DeleteSeedSuccess
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                _event.value = Event.Failed
            }
        }
    }

    sealed interface Event {
        object GetSeedSuccess : Event
        object DeleteSeedSuccess : Event

        object Failed : Event
    }
}
