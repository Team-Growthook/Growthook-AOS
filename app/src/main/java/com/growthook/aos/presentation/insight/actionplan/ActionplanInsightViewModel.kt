package com.growthook.aos.presentation.insight.actionplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActionplanInsightViewModel : ViewModel() {
    private val _actionplanList = MutableLiveData<List<String>>()
    val actionplanList: LiveData<List<String>> = _actionplanList
}
