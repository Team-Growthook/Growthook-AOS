package com.growthook.aos.presentation.insight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentInsightMenuBottomsheetBinding
import com.growthook.aos.util.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightMenuBottomsheet :
    BaseBottomSheetFragment<FragmentInsightMenuBottomsheetBinding>(R.layout.fragment_insight_menu_bottomsheet) {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentInsightMenuBottomsheetBinding =
        FragmentInsightMenuBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickMoveMenu()
    }

    private fun clickMoveMenu() {
        binding.clInsightMenuMove.setOnClickListener {
            dismiss()
            val bottomSheetDialog = InsightCaveBottomsheet()
            bottomSheetDialog.show(parentFragmentManager, "show")
        }
    }
}
