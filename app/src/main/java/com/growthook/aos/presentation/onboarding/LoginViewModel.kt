package com.growthook.aos.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import com.growthook.aos.domain.usecase.local.PostTokenUseCase
import com.growthook.aos.domain.usecase.local.PostUserUseCase
import com.growthook.aos.util.callback.KakaoLoginCallback
import com.growthook.aos.util.callback.KakaoUserCallback
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val postUserUseCase: PostUserUseCase,
    private val postTokenUseCase: PostTokenUseCase,
) :
    ViewModel() {
    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean> = _isLoginSuccess

    private val _isAlreadyLogin = MutableLiveData<Boolean>()
    val isAlreadyLogin: LiveData<Boolean> get() = _isAlreadyLogin

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        KakaoLoginCallback {
            _isLoginSuccess.value = true
            Timber.d("LoginViewModel 카카오 로그인 set 토큰 $token")
        }.handleResult(token, error)
    }

    val kakaoUserCallback: (User?, Throwable?) -> Unit = { user, error ->
        KakaoUserCallback { userName ->
            viewModelScope.launch {
                postUserUseCase.invoke(userName, 9, true)
                Timber.d("유저 닉네임: ${getUserUseCase.invoke().name}")
            }
        }.handleResult(user, error)
    }

    fun login(socialPlatform: String) = viewModelScope.launch {
        Timber.d("LoginViewModel 로그인 함수 호출")
    }

    fun checkIsAlreadyLogin() = viewModelScope.launch {
        var userInfo = getUserUseCase.invoke()
        _isAlreadyLogin.value = !userInfo.name.isNullOrBlank()
        Timber.d("자동 로그인 여부 ${isAlreadyLogin.value}")
        Timber.d("닉네임: ${getUserUseCase.invoke().name}")
    }
}
