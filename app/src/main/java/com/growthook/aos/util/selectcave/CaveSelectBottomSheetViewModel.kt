package com.growthook.aos.util.selectcave

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.domain.usecase.GetCavesUseCase
import com.growthook.aos.domain.usecase.MoveSeedUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CaveSelectBottomSheetViewModel @Inject constructor(
    private val getCavesUseCase: GetCavesUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val moveSeedUseCase: MoveSeedUseCase,
) : ViewModel() {

    private val _caves = MutableLiveData<List<Cave>>()
    val caves: LiveData<List<Cave>> = _caves

    private val _isMove = MutableLiveData<Boolean>()
    val isMove: LiveData<Boolean> = _isMove

    val selectedCave = MutableStateFlow<Cave?>(null)

    fun getCaves() {
        viewModelScope.launch {
            getCavesUseCase(DUMMY_MEMBER_ID).onSuccess { caves ->
                _caves.value = caves
            }
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

    companion object {
        const val DUMMY_MEMBER_ID = 3
    }
}
