package com.growthook.aos.presentation.cavecreate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.cavedetail.PostCaveUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import com.growthook.aos.domain.usecase.mypage.GetProfileUseCase
import com.growthook.aos.util.extension.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateNewCaveViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val postCaveUseCase: PostCaveUseCase,
    private val getProfileUseCase: GetProfileUseCase,
) : ViewModel() {

    private val _caveName = MutableLiveData<String>()
    val caveName: LiveData<String>
        get() = _caveName

    private val _caveIntroduction = MutableLiveData<String>()
    val caveIntroduction: LiveData<String>
        get() = _caveIntroduction

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String>
        get() = _nickName

    private val _postCaveSuccess = MutableLiveData<Boolean>()
    val postCaveSuccess: LiveData<Boolean>
        get() = _postCaveSuccess

    private val _profileUrl = MutableLiveData<String?>()
    val profileUrl: LiveData<String?> = _profileUrl

    fun getNickName() {
        viewModelScope.launch {
            getUserUseCase.invoke().name.let { nickName ->
                _nickName.value = nickName
            }
        }
        getProfileUrl()
    }

    fun getCaveName(name: String) {
        _caveName.value = name
    }

    fun getCaveIntroduction(introduction: String) {
        _caveIntroduction.value = introduction
    }

    val checkBtnEnabled = MediatorLiveData<Boolean>().apply {
        addSourceList(caveName, caveIntroduction) { checkCreateCaveEnabled() }
    }

    private fun checkCreateCaveEnabled(): Boolean =
        !caveName.value.isNullOrBlank() && !caveIntroduction.value.isNullOrBlank()

    fun postNewCave() {
        viewModelScope.launch {
            postCaveUseCase(
                getUserUseCase.invoke().memberId ?: 0,
                caveName.value.toString(),
                caveIntroduction.value.toString(),
            ).onSuccess {
                _postCaveSuccess.value = true
            }.onFailure {
                _postCaveSuccess.value = false
                Timber.d("CreateNewCave: ${it.message}")
            }
        }
    }

    private fun getProfileUrl() {
        viewModelScope.launch {
            getProfileUseCase.invoke(getUserUseCase.invoke().memberId ?: 0).onSuccess { profile ->
                _profileUrl.value = profile.profileUrl
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }
}
