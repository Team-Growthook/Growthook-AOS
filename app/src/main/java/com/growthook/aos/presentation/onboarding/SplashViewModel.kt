package com.growthook.aos.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _isOnboarding = MutableLiveData<Boolean>()
    val isOnboarding: LiveData<Boolean> = _isOnboarding

    init {
        viewModelScope.launch {
            _isOnboarding.value = getUserUseCase.invoke().isOnboarding
        }
    }
}
