package com.growthook.aos.presentation.cavedetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentCaveModifySelectBottomsheetBinding
import com.growthook.aos.presentation.cavedetail.cavemodify.CaveDetailModifyActivity
import com.growthook.aos.util.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            CaveDetailDeleteAlert().show(parentFragmentManager, "modify")
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
