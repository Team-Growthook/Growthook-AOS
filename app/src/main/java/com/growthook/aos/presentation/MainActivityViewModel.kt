package com.growthook.aos.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) :
    ViewModel() {

    private val _isAlreadyLogin = MutableLiveData<Boolean>()
    val isAlreadyLogin: LiveData<Boolean> get() = _isAlreadyLogin

    init {
        checkIsAlreadyLogin()
    }

    private fun checkIsAlreadyLogin() = viewModelScope.launch {
        var userInfo = getUserUseCase.invoke()
        _isAlreadyLogin.value = !userInfo.name.isNullOrBlank()
    }
}
