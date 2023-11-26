package com.growthook.aos.presentation.insight.noactionplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentInsightMenuBottomsheetBinding
import com.growthook.aos.util.base.BaseAlertDialog
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
        setClickListeners()
    }

    private fun setClickListeners() {
        clickMoveMenu()
        clickDeletedMenu()
        clickModifyMenu()
    }

    private fun clickMoveMenu() {
        binding.clInsightMenuMove.setOnClickListener {
            dismiss()
            val bottomSheetDialog = InsightCaveBottomsheet()
            bottomSheetDialog.show(parentFragmentManager, "show")
        }
    }

    private fun clickDeletedMenu() {
        binding.clInsightMenuDelete.setOnClickListener {
            dismiss()
            showDeleteDialog()
        }
    }

    private fun clickModifyMenu() {
        binding.clInsightMenuModify.setOnClickListener {
            // 수정하기 액티비티로 이동
        }
    }

    private fun showDeleteDialog() {
        BaseAlertDialog.Builder()
            .setCancelable(false)
            .build(
                type = BaseAlertDialog.DialogType.LEFT_INTENDED,
                title = "정말로 삭제할까요?",
                description = "삭제한 인사이트는 다시 복구할 수 없으니\n신중하게 결정해 주세요!",
                positiveText = "유지하기",
                negativeText = "삭제하기",
                tipText = "",
                isBackgroundImageVisility = false,
                isDescriptionVisility = true,
                isRemainThookVisility = false,
                isTipVisility = false,
                negativeAction = {
                    Toast.makeText(context, "씨앗이 삭제되었어요", Toast.LENGTH_SHORT).show()
                },
                positiveAction = {},
            ).show(parentFragmentManager, DELETE_DIALOG)
    }

    companion object {
        const val DELETE_DIALOG = "delete dialog"
    }
}
