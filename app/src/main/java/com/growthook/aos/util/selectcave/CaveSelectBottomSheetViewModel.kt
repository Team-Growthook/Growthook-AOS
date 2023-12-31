package com.growthook.aos.util.selectcave

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.domain.usecase.GetCavesUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaveSelectBottomSheetViewModel @Inject constructor(
    private val getCavesUseCase: GetCavesUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _caves = MutableLiveData<List<Cave>>()
    val caves: LiveData<List<Cave>> = _caves

    val selectedCave = MutableStateFlow<Cave?>(null)

    fun getCaves() {
        viewModelScope.launch {
            getCavesUseCase(getUserUseCase.invoke()?.memberId ?: 0).onSuccess { caves ->
                _caves.value = caves
            }
        }
    }
}
