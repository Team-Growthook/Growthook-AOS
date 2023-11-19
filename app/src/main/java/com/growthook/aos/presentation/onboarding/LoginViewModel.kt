package com.growthook.aos.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.util.extension.KakaoLoginCallback
import com.kakao.sdk.auth.model.OAuthToken
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel() :
    ViewModel() {
    private val _isKakaoLogin = MutableLiveData<Boolean>()
    val isKakaoLogin: LiveData<Boolean> = _isKakaoLogin

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        KakaoLoginCallback {
            _isKakaoLogin.value = true
            Timber.d("LoginViewModel", "카카오 로그인 set 토큰 $token")
        }.handleResult(token, error)
    }

    fun login(socialPlatform: String) = viewModelScope.launch {
        Timber.d("LoginViewModel", "로그인 함수 호출")
    }
}
