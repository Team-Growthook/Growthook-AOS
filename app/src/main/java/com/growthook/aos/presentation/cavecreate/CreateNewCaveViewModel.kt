package com.growthook.aos.presentation.cavecreate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.usecase.PostCaveUseCase
import com.growthook.aos.domain.usecase.local.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewCaveViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val postCaveUseCase: PostCaveUseCase
) : ViewModel() {

    private val _storageName = MutableLiveData<String>()
    val storageName: LiveData<String>
        get() = _storageName

    private val _storageIntroduction = MutableLiveData<String>()
    val storageIntroduction: LiveData<String>
        get() = _storageIntroduction

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String>
        get() = _nickName

    fun getNickName() {
        viewModelScope.launch {
            getUserUseCase.invoke().name.let {  nickName ->
                _nickName.value = nickName
            }
        }
    }

    fun getStorageName(name: String) {
        _storageName.value = name
    }

    fun getStorageIntroduction(introduction: String) {
        _storageIntroduction.value = introduction
    }

    val checkBtnEnabled = MediatorLiveData<Boolean>().apply {
        addSource(storageName) { value = checkCreateStorageEnabled() }
        addSource(storageIntroduction) { value = checkCreateStorageEnabled() }
    }

    private fun checkCreateStorageEnabled(): Boolean =
        !storageName.value.isNullOrBlank() && !storageIntroduction.value.isNullOrBlank()
}