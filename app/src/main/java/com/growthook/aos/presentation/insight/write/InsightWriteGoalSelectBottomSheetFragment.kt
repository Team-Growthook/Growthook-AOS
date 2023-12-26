package com.growthook.aos.presentation.insight.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentInsightWriteGoalSelectBottomsheetBinding
import com.growthook.aos.util.base.BaseBottomSheetFragment

class InsightWriteGoalSelectBottomSheetFragment :
    BaseBottomSheetFragment<FragmentInsightWriteGoalSelectBottomsheetBinding>(R.layout.fragment_insight_write_goal_select_bottomsheet) {

    private lateinit var onGoalSelectedListener: OnGoalSelectedListener

    interface OnGoalSelectedListener {
        fun onGoalSelected(goalMonth: Int)
    }

    fun setOnGoalSelectedListener(listener: OnGoalSelectedListener) {
        onGoalSelectedListener = listener
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentInsightWriteGoalSelectBottomsheetBinding =
        FragmentInsightWriteGoalSelectBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSetGoalMonthPicker()
    }

    private fun initSetGoalMonthPicker() {
        val goalPicker = binding.pickerInsightGoalMonth

        with (goalPicker) {
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            minValue = 1
            maxValue = 12
            displayedValues = (goalPicker.minValue..goalPicker.maxValue).map { it.toString() + "개월" }.toTypedArray()
        }
        clickGoalMonthBtn()
    }

    private fun clickGoalMonthBtn() {
        binding.btnInsightGoal.setOnClickListener {
            val goalMonth = binding.pickerInsightGoalMonth.value
            onGoalSelectedListener.onGoalSelected(goalMonth)
            dismiss()
        }
    }
}