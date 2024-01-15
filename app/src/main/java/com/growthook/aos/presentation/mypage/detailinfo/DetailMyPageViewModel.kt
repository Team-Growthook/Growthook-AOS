package com.growthook.aos.presentation.mypage.detailinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import com.growthook.aos.domain.usecase.local.PostUserUseCase
import com.growthook.aos.domain.usecase.mypage.DeleteMemberUseCase
import com.growthook.aos.domain.usecase.mypage.GetEmailUseCase
import com.growthook.aos.util.callback.KakaoLogoutCallback
import com.growthook.aos.util.extension.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailMyPageViewModel @Inject constructor(
    private val postUserUseCase: PostUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getEmailUseCase: GetEmailUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase,
) :
    ViewModel() {

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val isMemberDelete = MutableLiveData<Boolean>()
    private val isKakaoDeleteAccount = MutableLiveData(false)

    val kakaoCallback: (Throwable?) -> Unit = { error ->
        KakaoLogoutCallback {
            isKakaoDeleteAccount.value = it
            viewModelScope.launch {
                postUserUseCase.invoke("", 0)
            }
        }.handleResult(error)
    }
    val isDeleteSuccess = MediatorLiveData<Boolean>().apply {
        addSourceList(isMemberDelete, isKakaoDeleteAccount) { checkIsDelete() }
    }

    private fun checkIsDelete(): Boolean =
        isKakaoDeleteAccount.value == true && isMemberDelete.value == true

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

    fun deleteMember() {
        viewModelScope.launch {
            deleteMemberUseCase.invoke(5).onSuccess {
                isMemberDelete.value = true
            }.onFailure {
                isMemberDelete.value = false
                Timber.e(it.message)
            }
        }
    }
}
