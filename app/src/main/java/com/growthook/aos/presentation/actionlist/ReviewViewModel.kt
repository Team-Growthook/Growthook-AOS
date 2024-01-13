package com.growthook.aos.presentation.actionlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growthook.aos.domain.entity.Review
import com.growthook.aos.domain.usecase.review.GetReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val getReviewUseCase: GetReviewUseCase,
) : ViewModel() {
    private val _reviewDetail = MutableLiveData<Review>()
    val reviewDetail: MutableLiveData<Review> = _reviewDetail

    fun getReviewDetail(actionplanId: Int) {
        viewModelScope.launch {
            getReviewUseCase.invoke(actionplanId).onSuccess {
                _reviewDetail.value = it
            }
        }
    }
}
