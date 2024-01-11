package com.growthook.aos.presentation.actionlist

import android.os.Bundle
import com.growthook.aos.databinding.ActivityReviewDetailBinding
import com.growthook.aos.util.base.BaseActivity

class ReviewDetailActivity :
    BaseActivity<ActivityReviewDetailBinding>({ ActivityReviewDetailBinding.inflate(it) }) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickBackBtn()
    }
    private fun clickBackBtn() {
        binding.ivReviewDetailBack.setOnClickListener {
            finish()
        }
    }
}
