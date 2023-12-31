package com.growthook.aos.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import com.growthook.aos.domain.usecase.local.PostUserUseCase
import com.growthook.aos.util.callback.KakaoLogoutCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val postUserUseCase: PostUserUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _isLogoutSuccess = MutableLiveData<Boolean>()
    val isLogoutSuccess: LiveData<Boolean> = _isLogoutSuccess

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName

    val kakaoLogoutCallback: (Throwable?) -> Unit = { error ->
        KakaoLogoutCallback {
            Timber.d("로그아웃 성공 여부 $it")
            _isLogoutSuccess.value = it
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
    }
}
