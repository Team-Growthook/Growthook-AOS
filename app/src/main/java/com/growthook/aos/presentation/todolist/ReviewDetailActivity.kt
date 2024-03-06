package com.growthook.aos.presentation.todolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityReviewDetailBinding
import com.growthook.aos.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ReviewDetailActivity :
    BaseActivity<ActivityReviewDetailBinding>({ ActivityReviewDetailBinding.inflate(it) }) {
    private val viewModel by viewModels<ReviewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionplanId = intent.getIntExtra(ACTIONPLAN_ID, 0)
        Timber.d("액션플랜 id $actionplanId")
        viewModel.getReviewDetail(actionplanId)
        observeReviewDetail()
        clickBackBtn()
    }

    private fun clickBackBtn() {
        binding.ivReviewDetailBack.setOnClickListener {
            finish()
        }
    }

    private fun observeReviewDetail() {
        viewModel.reviewDetail.observe(this) {
            with(binding) {
                tvReviewDetailTitle.text = it.actionPlan
                tvReviewDetailContent.text = it.content
                tvReviewDetailDate.text = it.reviewDate
                if (it.isScraped) {
                    ivReviewDetailTitle.setImageResource(R.drawable.ic_scrap_selected)
                } else {
                    ivReviewDetailTitle.setImageResource(R.drawable.ic_scrap_unselected)
                }
            }
        }
    }

    companion object {
        private const val ACTIONPLAN_ID = "actionplanId"
        fun getIntent(context: Context, actionplanId: Int): Intent {
            return Intent(context, ReviewDetailActivity::class.java).apply {
                putExtra(ACTIONPLAN_ID, actionplanId)
            }
        }
    }
}
