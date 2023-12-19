package com.growthook.aos.presentation.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentSelectMenuBottomsheetBinding
import com.growthook.aos.presentation.insight.noactionplan.InsightMenuBottomsheet
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.base.BaseBottomSheetFragment
import com.growthook.aos.util.selectcave.CaveSelect
import timber.log.Timber

class SelectMenuBottomSheet() :
    BaseBottomSheetFragment<FragmentSelectMenuBottomsheetBinding>(R.layout.fragment_select_menu_bottomsheet) {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSelectMenuBottomsheetBinding =
        FragmentSelectMenuBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDisableDim()
        clickMoveBtn()
        clickDeleteBtn()
    }

    private fun clickMoveBtn() {
        binding.btnHomeSelectMove.setOnClickListener {
            CaveSelect.Builder().build {
                Toast.makeText(
                    requireContext(),
                    "${it?.name}에 ${viewModel.longClickInsight.value}를 옮깁니다",
                    Toast.LENGTH_SHORT,
                ).show()
            }.show(parentFragmentManager, "select")
        }
    }

    private fun clickDeleteBtn() {
        binding.btnHomeSelectDelete.setOnClickListener {
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
                ).show(parentFragmentManager, InsightMenuBottomsheet.DELETE_DIALOG)
        }
    }

    private fun setDisableDim() {
        context?.let {
            dialog?.let { dialog ->
                dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        viewModel.isMenuDismissed.value = true
    }
}
