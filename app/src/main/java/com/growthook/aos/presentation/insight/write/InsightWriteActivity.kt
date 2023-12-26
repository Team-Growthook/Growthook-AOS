package com.growthook.aos.presentation.insight.write

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivityInsightWriteBinding
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightWriteActivity : BaseActivity<ActivityInsightWriteBinding>({
    ActivityInsightWriteBinding.inflate(it)
}) {

    private val viewModel by viewModels<InsightWriteViewModel>()
    private lateinit var caveSelectBottomSheet: InsightWriteCaveSelectBottomSheetFragment
    private lateinit var goalSelectBottomSheet: InsightWriteGoalSelectBottomSheetFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsightWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSetSelectCaveBottomSheet()
        initSetSelectGoalBottomSheet()

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.currentFocus?.let { hideKeyboard(it) }
        with(binding) {
            edtInsightWriteInsight.clearFocus()
            edtInsightWriteMemo.clearFocus()
            edtInsightWriteUrl.clearFocus()
            edtInsightWriteUrlChoice.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun initSetSelectCaveBottomSheet() {
        caveSelectBottomSheet = InsightWriteCaveSelectBottomSheetFragment()

        binding.layoutInsightWriteCave.setOnClickListener {
            binding.layoutInsightWriteCave.requestFocusFromTouch()
            caveSelectBottomSheet.setOnCaveSelectedListener(object :
                InsightWriteCaveSelectBottomSheetFragment.OnCaveSelectedListener {
                override fun onCaveSelected(caveName: String) {
                    setSelectedCaveEditText(caveName)
                    viewModel.setSelectedCaveName(caveName)
                }
            })
            caveSelectBottomSheet.show(supportFragmentManager, TAG_BOTTOM_SHEET)
        }
    }

    private fun setSelectedCaveEditText(caveName: String) {
        with(binding) {
            tvInsightWriteCaveHint.visibility = View.GONE
            tvInsightWriteCaveSelected.visibility = View.VISIBLE
            tvInsightWriteCaveSelected.text = caveName
        }
    }

    private fun initSetSelectGoalBottomSheet() {
        goalSelectBottomSheet = InsightWriteGoalSelectBottomSheetFragment()

        binding.layoutInsightWriteGoal.setOnClickListener {
            goalSelectBottomSheet.setOnGoalSelectedListener(object :
                InsightWriteGoalSelectBottomSheetFragment.OnGoalSelectedListener {
                override fun onGoalSelected(goalMonth: Int) {
                    setSelectedGoalEditText(goalMonth)
                    viewModel.setSelectedGoalMonth(goalMonth)
                }
            })
            goalSelectBottomSheet.show(supportFragmentManager, TAG_BOTTOM_SHEET)
        }
    }

    private fun setSelectedGoalEditText(goalMonth: Int) {
        with(binding) {
            tvInsightWriteGoalHint.visibility = View.GONE
            tvInsightWriteGoalSelected.visibility = View.VISIBLE
            tvInsightWriteGoalSelected.text = goalMonth.toString() + DISPLAY_GOAL_MONTH
        }
    }

    companion object {
        const val TAG_BOTTOM_SHEET = "SHOW"
        const val DISPLAY_GOAL_MONTH = "개월"
    }
}