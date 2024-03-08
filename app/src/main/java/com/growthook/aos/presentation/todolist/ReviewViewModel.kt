package com.growthook.aos.presentation.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Review
import com.growthook.aos.domain.usecase.review.GetReviewUseCase
import com.growthook.aos.presentation.insight.noactionplan.add.AddActionplanViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val getReviewUseCase: GetReviewUseCase,
) : ViewModel() {
    private val _reviewDetail = MutableLiveData<Review>()
    val reviewDetail: MutableLiveData<Review> = _reviewDetail

    private val _event = MutableLiveData<AddActionplanViewModel.Event>()
    val event: LiveData<AddActionplanViewModel.Event> = _event

    fun getReviewDetail(actionplanId: Int) {
        viewModelScope.launch {
            getReviewUseCase.invoke(actionplanId).onSuccess {
                _reviewDetail.value = it
            }
        }
    }
}
