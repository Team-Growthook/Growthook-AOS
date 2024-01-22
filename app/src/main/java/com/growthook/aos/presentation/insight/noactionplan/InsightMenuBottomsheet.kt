package com.growthook.aos.presentation.insight.noactionplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentInsightMenuBottomsheetBinding
import com.growthook.aos.presentation.insight.noactionplan.NoActionplanInsightViewModel.Event
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.base.BaseBottomSheetFragment
import com.growthook.aos.util.selectcave.CaveSelect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightMenuBottomsheet :
    BaseBottomSheetFragment<FragmentInsightMenuBottomsheetBinding>(R.layout.fragment_insight_menu_bottomsheet) {
    private val viewModel by activityViewModels<NoActionplanInsightViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentInsightMenuBottomsheetBinding =
        FragmentInsightMenuBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        observeEvent()
    }

    private fun setClickListeners() {
        clickMoveMenu()
        clickDeletedMenu()
        clickModifyMenu()
    }

    private fun clickMoveMenu() {
        binding.clInsightMenuMove.setOnClickListener {
            dismiss()
            CaveSelect.Builder().build(
                CaveSelect.CaveSelectType.YES_API,
                clickBtnAction = {
                    viewModel.seedId
                },
            ).show(parentFragmentManager, CAVE_SELECT_DIALOG)
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
            // TODO 수정 필요
//            val intent = Intent(requireActivity(), SeedModifyActivity::class.java)
//            viewModel.seedData.observe(viewLifecycleOwner) { seed ->
//                intent.putExtra(
//                    SEED_MODIFY_INTENT,
//                    SeedModifyIntent(
//                        seed.title,
//                        seed.content,
//                        seed.caveName,
//                        seed.source,
//                        seed.url,
//                        seed.remainingDays / 30,
//                    ),
//                )
//                startActivity(intent)
//                dismiss()
//            }
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
                    viewModel.deleteSeed()
                },
                positiveAction = {},
            ).show(parentFragmentManager, DELETE_DIALOG)
    }

    private fun observeEvent() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is Event.DeleteSeedSuccess -> {
                    Toast.makeText(context, "씨앗이 삭제되었어요", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    companion object {
        const val DELETE_DIALOG = "delete dialog"
        private const val DUMMY_SEED = 113
        const val SEED_MODIFY_INTENT = "SEED_MODIFY_INTENT"
        const val CAVE_SELECT_DIALOG = "cave select dialog"
    }
}
