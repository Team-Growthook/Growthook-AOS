package com.growthook.aos.presentation.cavedetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentCaveModifySelectBottomsheetBinding
import com.growthook.aos.presentation.MainActivity
import com.growthook.aos.presentation.cavedetail.cavemodify.CaveDetailModifyActivity
import com.growthook.aos.presentation.insight.noactionplan.see.InsightMenuBottomsheet
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.base.BaseBottomSheetFragment

class CaveModifySelectBottomSheet :
    BaseBottomSheetFragment<FragmentCaveModifySelectBottomsheetBinding>(R.layout.fragment_cave_modify_select_bottomsheet) {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCaveModifySelectBottomsheetBinding =
        FragmentCaveModifySelectBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickDelete()
        clickModify()
    }

    private fun clickDelete() {
        binding.btnCaveDetailDelete.setOnClickListener {
            dismiss()
            BaseAlertDialog.Builder()
                .setCancelable(false)
                .build(
                    type = BaseAlertDialog.DialogType.LEFT_INTENDED,
                    title = "정말로 삭제할까요?",
                    description = "삭제한 보관함은 다시 복구할 수 없으니\n신중하게 결정해 주세요!",
                    positiveText = "유지하기",
                    negativeText = "삭제하기",
                    tipText = "",
                    isBackgroundImageVisility = false,
                    isDescriptionVisility = true,
                    isRemainThookVisility = false,
                    isTipVisility = false,
                    negativeAction = {
                        Toast.makeText(context, "동굴이 삭제되었어요", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    },
                    positiveAction = {},
                ).show(parentFragmentManager, InsightMenuBottomsheet.DELETE_DIALOG)
        }
    }

    private fun clickModify() {
        binding.btnCaveDetailModify.setOnClickListener {
            val intent = Intent(requireActivity(), CaveDetailModifyActivity::class.java)
            intent.putExtra("caveName", "동굴 이름이름")
            startActivity(intent)
            dismiss()
        }
    }
}
