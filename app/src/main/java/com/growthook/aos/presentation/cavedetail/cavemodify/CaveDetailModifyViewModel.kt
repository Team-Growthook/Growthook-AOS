package com.growthook.aos.presentation.cavedetail.cavemodify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CaveDetailModifyViewModel @Inject constructor() : ViewModel() {

    private val _isCaveModified = MutableLiveData<Boolean>(false)
    val isCaveModified: LiveData<Boolean>
        get() = _isCaveModified

    fun setIsModified(modify: Boolean) {
        _isCaveModified.value = modify
    }
}
