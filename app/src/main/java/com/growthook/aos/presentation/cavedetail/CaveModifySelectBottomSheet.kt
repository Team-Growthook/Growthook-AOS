package com.growthook.aos.presentation.cavedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentCaveModifySelectBottomsheetBinding
import com.growthook.aos.util.base.BaseBottomSheetFragment

class CaveModifySelectBottomSheet :
    BaseBottomSheetFragment<FragmentCaveModifySelectBottomsheetBinding>(R.layout.fragment_cave_modify_select_bottomsheet) {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCaveModifySelectBottomsheetBinding =
        FragmentCaveModifySelectBottomsheetBinding.inflate(inflater, container, false)
}
