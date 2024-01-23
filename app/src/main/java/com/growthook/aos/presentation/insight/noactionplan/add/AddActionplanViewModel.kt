package com.growthook.aos.presentation.insight.noactionplan.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Seed
import com.growthook.aos.domain.usecase.actionplan.PostActionplansUseCase
import com.growthook.aos.domain.usecase.seeddetail.GetSeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddActionplanViewModel @Inject constructor(
    private val postActionplansUseCase: PostActionplansUseCase,
    private val getSeedUseCase: GetSeedUseCase,
) : ViewModel() {
    var seedId: Int = 0

    private val _actionplanList = MutableLiveData(mutableListOf(""))
    val actionplanList: LiveData<MutableList<String>> = _actionplanList

    val isButtonEnabled = MutableLiveData(false)

    private val _seedData = MutableStateFlow<Seed?>(null)
    val seedData: MutableStateFlow<Seed?> = _seedData

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

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

    fun postActionplans(seedId: Int, actionplans: List<String>) {
        viewModelScope.launch {
            postActionplansUseCase.invoke(seedId, actionplans).onSuccess {
                _event.value = Event.PostSuccess
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                _event.value = Event.PostFailed
            }
        }
    }

    fun addItem(item: String) {
        if (!item.isNullOrBlank()) {
            val currentItems = _actionplanList.value.orEmpty().toMutableList()
            currentItems.add(0, item)
            _actionplanList.value = currentItems
        }
    }

    fun updateItem(position: Int, text: String) {
        val currentItems = _actionplanList.value.orEmpty().toMutableList()
        if (position < currentItems.size) {
            currentItems[position] = text
            _actionplanList.value = currentItems
        }
    }

    sealed interface Event {
        object PostFailed : Event
        object PostSuccess : Event
        object GetSeedSuccess : Event
        object Failed : Event
    }
}
