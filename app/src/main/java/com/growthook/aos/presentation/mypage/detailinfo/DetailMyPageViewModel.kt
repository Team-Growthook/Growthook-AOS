package com.growthook.aos.presentation.mypage.detailinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import com.growthook.aos.domain.usecase.local.PostUserUseCase
import com.growthook.aos.domain.usecase.mypage.GetEmailUseCase
import com.growthook.aos.util.callback.KakaoLogoutCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailMyPageViewModel @Inject constructor(
    private val postUserUseCase: PostUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getEmailUseCase: GetEmailUseCase,
) :
    ViewModel() {

    private val _isKakaoDeleteAccount = MutableLiveData(false)
    val isKakaoDeleteAccount: LiveData<Boolean> = _isKakaoDeleteAccount

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    val kakaoCallback: (Throwable?) -> Unit = { error ->
        KakaoLogoutCallback {
            _isKakaoDeleteAccount.value = true
            viewModelScope.launch {
                postUserUseCase.invoke("", 0)
            }
        }.handleResult(error)
    }

    init {
        viewModelScope.launch {
            getUserUseCase.invoke().name.let { nickName ->
                _nickName.value = nickName
            }
        }
        getEmail()
    }

    private fun getEmail() {
        viewModelScope.launch {
            getEmailUseCase.invoke(4).onSuccess { email ->
                _email.value = email
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }
}
