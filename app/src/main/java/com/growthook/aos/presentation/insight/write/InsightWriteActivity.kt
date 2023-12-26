package com.growthook.aos.presentation.insight.write

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivityInsightWriteBinding
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightWriteActivity: BaseActivity<ActivityInsightWriteBinding>({
    ActivityInsightWriteBinding.inflate(it)
}) {

    private val viewModel by viewModels<InsightWriteViewModel>()
    private lateinit var caveSelectBottomSheet : InsightWriteCaveSelectBottomSheetFragment
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
            //edtInsightWriteGoal.clearFocus()
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
                    setCaveSelectedEditText(caveName)
                    viewModel.setSelectedCaveName(caveName)
                }
            }
            )
            caveSelectBottomSheet.show(supportFragmentManager, TAG_BOTTOM_SHEET)
        }
    }

    private fun setCaveSelectedEditText(caveName: String) {
        with (binding) {
            tvInsightWriteCaveHint.visibility = View.GONE
            tvInsightWriteCaveSelected.visibility = View.VISIBLE
            tvInsightWriteCaveSelected.text = caveName
        }
    }

    private fun initSetSelectGoalBottomSheet() {
        goalSelectBottomSheet = InsightWriteGoalSelectBottomSheetFragment()

        binding.layoutInsightWriteGoal.setOnClickListener {
            goalSelectBottomSheet.show(supportFragmentManager, TAG_BOTTOM_SHEET)
        }
    }

    companion object {
        const val TAG_BOTTOM_SHEET = "SHOW"
    }
}