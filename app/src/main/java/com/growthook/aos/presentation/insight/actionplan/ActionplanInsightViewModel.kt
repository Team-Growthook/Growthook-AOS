package com.growthook.aos.presentation.insight.actionplan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Actionplan
import com.growthook.aos.domain.entity.Seed
import com.growthook.aos.domain.usecase.seeddetail.GetSeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ActionplanInsightViewModel @Inject constructor(
    private val getSeedUseCase: GetSeedUseCase,
) : ViewModel() {
    var actionplanList: MutableLiveData<List<Actionplan>> = MutableLiveData<List<Actionplan>>()

    private val _seedData = MutableLiveData<Seed>()
    val seedData: LiveData<Seed> = _seedData

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    init {
        getSeedDetail(45)
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

//    init {
//        actionplanList.value = listOf(
//            Actionplan(1, 1, "아"),
//            Actionplan(2, 2, "아아"),
//            Actionplan(3, 3, "아아아"),
//            Actionplan(4, 4, "아아아아"),
//            Actionplan(5, 5, "아아아아아"),
//            Actionplan(6, 6, "아아아아아아"),
//            Actionplan(7, 7, "아아아아아아아"),
//            Actionplan(8, 8, "아아아아아아아아"),
//            Actionplan(9, 9, "아아아아아아아아아"),
//
//        )
//    }

    sealed interface Event {
        object Failed : Event
        object Success : Event
    }

    fun deleteActionplan(position: Int) {
        val currentList = actionplanList.value.orEmpty().toMutableList()
        if (position in currentList.indices) {
            currentList.removeAt(position)
            actionplanList.value = currentList.toList()
        }
    }
}
