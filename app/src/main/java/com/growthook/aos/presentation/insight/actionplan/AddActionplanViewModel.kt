package com.growthook.aos.presentation.insight.actionplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddActionplanViewModel : ViewModel() {
    private val _actionplanList = MutableLiveData<MutableList<String>>()
    val actionplanList: LiveData<MutableList<String>> = _actionplanList

    val isButtonEnabled = MutableLiveData<Boolean>(false)

    fun addItem(item: String) {
        val currentItems = _actionplanList.value.orEmpty().toMutableList()
        currentItems.add(0, item)
        _actionplanList.value = currentItems
    }

    fun updateItem(position: Int, text: String) {
        val currentItems = _actionplanList.value.orEmpty().toMutableList()
        if (position < currentItems.size) {
            currentItems[position] = text
            _actionplanList.value = currentItems
        }
    }
}
