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

        goalPicker.wrapSelectorWheel = false
        goalPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        goalPicker.minValue = 1
        goalPicker.maxValue = 12
        goalPicker.displayedValues = (goalPicker.minValue..goalPicker.maxValue).map { it.toString() + "개월" }.toTypedArray()

        binding.btnInsightGoal.setOnClickListener {
            sendGoalMonthData()
            dismiss()
        }
    }

    private fun sendGoalMonthData() {
//        val goalPicker = binding.pickerInsightGoalMonth
//
//        goalPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
//        goalPicker.displayedValues = arrayOf(goalPicker.value.toString() + "개월")


    }
}