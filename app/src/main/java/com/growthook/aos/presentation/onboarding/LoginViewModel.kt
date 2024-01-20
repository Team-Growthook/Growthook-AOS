package com.growthook.aos.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import com.growthook.aos.domain.usecase.onboarding.SignUpUseCase
import com.growthook.aos.util.callback.KakaoLoginCallback
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val postAuthUserUseCase: SignUpUseCase,
) :
    ViewModel() {
    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean> = _isLoginSuccess

    private val _isAlreadyLogin = MutableLiveData<Boolean>()
    val isAlreadyLogin: LiveData<Boolean> get() = _isAlreadyLogin

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        KakaoLoginCallback {
            viewModelScope.launch {
                token?.let {
                    _isLoginSuccess.value =
                        postAuthUserUseCase.invoke("KAKAO", "Bearer ${token.accessToken}").isSuccess
                }
            }
        }.handleResult(token, error)
    }

    fun checkIsAlreadyLogin() = viewModelScope.launch {
        var userInfo = getUserUseCase.invoke()
        _isAlreadyLogin.value = !userInfo.name.isNullOrBlank()
        Timber.d("자동 로그인 여부 ${isAlreadyLogin.value}")
        Timber.d("닉네임: ${getUserUseCase.invoke().name}")
    }
}
