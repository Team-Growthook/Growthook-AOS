package com.growthook.aos.presentation.cavedetail.cavemodify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.cavedetail.ModifyCaveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CaveDetailModifyViewModel @Inject constructor(
    private val modifyCaveUseCase: ModifyCaveUseCase,
) : ViewModel() {

    private val _isCaveModified = MutableLiveData<Boolean>(false)
    val isCaveModified: LiveData<Boolean> = _isCaveModified

    private val _isModifySuccess = MutableLiveData<Boolean>()
    val isModifySuccess: LiveData<Boolean> = _isModifySuccess

    private val _caveId = MutableLiveData<Int>()
    val caveId: LiveData<Int> = _caveId

    fun setCaveId(caveId: Int) {
        _caveId.value = caveId
    }

    fun setIsModified(modify: Boolean) {
        _isCaveModified.value = modify
    }

    fun modifyCave(name: String, introduction: String) {
        viewModelScope.launch {
            modifyCaveUseCase(_caveId.value ?: 0, name, introduction).onSuccess {
                _isModifySuccess.value = true
            }.onFailure {
                _isModifySuccess.value = false
                Timber.e(it.message)
            }
        }
    }
}
