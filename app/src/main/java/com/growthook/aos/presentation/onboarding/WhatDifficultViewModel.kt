package com.growthook.aos.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growthook.aos.util.extension.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WhatDifficultViewModel @Inject constructor() : ViewModel() {

    private val _forget = MutableLiveData(false)
    val forget: LiveData<Boolean> = _forget

    private val _notFind = MutableLiveData(false)
    val notFind: LiveData<Boolean> = _notFind

    private val _notPractice = MutableLiveData(false)
    val notPractice: LiveData<Boolean> = _notPractice

    private val _notGrow = MutableLiveData(false)
    val notGrow: LiveData<Boolean> = _notGrow

    val isBtnEnable = MediatorLiveData<Boolean>().apply {
        addSourceList(forget, notFind, notPractice, notGrow) { checkEnableBtn() }
    }

    private fun checkEnableBtn(): Boolean =
        forget.value == true || notFind.value == true || notPractice.value == true || notGrow.value == true

    fun changeForget() {
        _forget.value = !requireNotNull(_forget.value)
    }

    fun changeNotFind() {
        _notFind.value = !requireNotNull(_notFind.value)
    }

    fun changeNotPractice() {
        _notPractice.value = !requireNotNull(_notPractice.value)
    }

    fun changeNotGrow() {
        _notGrow.value = !requireNotNull(_notGrow.value)
    }
}
