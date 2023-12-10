package com.growthook.aos.presentation.mypage.detailinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.local.PostUserUseCase
import com.growthook.aos.util.callback.KakaoLogoutCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMyPageViewModel @Inject constructor(private val postUserUseCase: PostUserUseCase) :
    ViewModel() {

    private val _isKakaoDeleteAccount = MutableLiveData(false)
    val isKakaoDeleteAccount: LiveData<Boolean> = _isKakaoDeleteAccount

    val kakaoCallback: (Throwable?) -> Unit = { error ->
        KakaoLogoutCallback {
            _isKakaoDeleteAccount.value = true
            viewModelScope.launch {
                postUserUseCase.invoke("")
            }
        }.handleResult(error)
    }
}
