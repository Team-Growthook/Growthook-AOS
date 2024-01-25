package com.growthook.aos.presentation.cavedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentCaveModifySelectBottomsheetBinding
import com.growthook.aos.presentation.cavedetail.cavemodify.CaveDetailModifyActivity
import com.growthook.aos.presentation.model.CaveModifyIntent
import com.growthook.aos.util.base.BaseBottomSheetFragment
import com.growthook.aos.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaveModifySelectBottomSheet :
    BaseBottomSheetFragment<FragmentCaveModifySelectBottomsheetBinding>(R.layout.fragment_cave_modify_select_bottomsheet) {

    private val viewModel by activityViewModels<CaveDetailViewModel>()
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
            CaveDetailDeleteAlert().show(parentFragmentManager, "modify")
        }
    }

    private fun clickModify() {
        binding.btnCaveDetailModify.setOnSingleClickListener {
            val caveModifyIntent = CaveModifyIntent(
                viewModel.caveId.value,
                viewModel.caveDetail.value?.caveName ?: "",
                viewModel.caveDetail.value?.introduction ?: "",
            )
            startActivity(CaveDetailModifyActivity.getIntent(requireContext(), caveModifyIntent))
            dismiss()
        }
    }
}
