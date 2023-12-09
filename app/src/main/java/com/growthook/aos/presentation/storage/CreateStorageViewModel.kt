package com.growthook.aos.presentation.storage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateStorageViewModel @Inject constructor() : ViewModel() {

    private val _storageName = MutableLiveData<String>()
    val storageName: LiveData<String>
        get() = _storageName

    private val _storageIntroduction = MutableLiveData<String>()
    val storageIntroduction: LiveData<String>
        get() = _storageIntroduction

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