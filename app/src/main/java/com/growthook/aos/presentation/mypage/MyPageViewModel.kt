package com.growthook.aos.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.GetGatherdThookUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import com.growthook.aos.domain.usecase.local.PostUserUseCase
import com.growthook.aos.domain.usecase.mypage.GetUsedThookUseCase
import com.growthook.aos.util.callback.KakaoLogoutCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val postUserUseCase: PostUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getGatherdThookUseCase: GetGatherdThookUseCase,
    private val getUsedThookUseCase: GetUsedThookUseCase,
) : ViewModel() {

    private val _isLogoutSuccess = MutableLiveData<Boolean>()
    val isLogoutSuccess: LiveData<Boolean> = _isLogoutSuccess

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName

    private val _gatherdThook = MutableLiveData<Int>()
    val gatherdThook: LiveData<Int> = _gatherdThook

    private val _usedThook = MutableLiveData<Int>()
    val usedThook: LiveData<Int> = _usedThook

    private val memberId = MutableLiveData<Int>(0)

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
            _nickName.value = getUserUseCase.invoke().name ?: ""
//            memberId.value = 4
        }
        getGatherdThook()
        getUsedThook()
    }

    fun getGatherdThook() {
        viewModelScope.launch {
            getGatherdThookUseCase.invoke(memberId.value ?: 0).onSuccess { thookCount ->
                _gatherdThook.value = thookCount
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }

    fun getUsedThook() {
        viewModelScope.launch {
            getUsedThookUseCase.invoke(memberId.value ?: 0).onSuccess { thookCount ->
                _usedThook.value = thookCount
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }
}
