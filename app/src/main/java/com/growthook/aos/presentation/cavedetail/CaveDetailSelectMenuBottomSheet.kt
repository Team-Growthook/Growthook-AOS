package com.growthook.aos.presentation.cavedetail

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
import com.growthook.aos.util.extension.setOnSingleClickListener
import com.growthook.aos.util.selectcave.CaveSelect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaveDetailSelectMenuBottomSheet() :
    BaseBottomSheetFragment<FragmentSelectMenuBottomsheetBinding>(R.layout.fragment_select_menu_bottomsheet) {

    private val viewModel: CaveDetailViewModel by activityViewModels()

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
        binding.btnHomeSelectMove.setOnSingleClickListener {
            CaveSelect.Builder().build(
                CaveSelect.CaveSelectType.YES_API,
                viewModel.longClickInsight.value?.seedId ?: 1,
            ).show(parentFragmentManager, "select")
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
                        viewModel.deleteSeed(viewModel.longClickInsight.value?.seedId ?: 0)
                        viewModel.isSeedDelete.observe(viewLifecycleOwner) { isDelete ->
                            if (isDelete) {
                                Toast.makeText(context, "씨앗이 삭제되었어요", Toast.LENGTH_SHORT).show()
                                dismiss()
                            }
                        }
                    },
                    positiveAction = {},
                    remainThookText = "",
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
