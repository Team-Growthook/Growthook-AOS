package com.growthook.aos.util.selectcave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentCaveSelectBottomsheetBinding
import com.growthook.aos.util.base.BaseBottomSheetFragment

abstract class CaveSelect :
    BaseBottomSheetFragment<FragmentCaveSelectBottomsheetBinding>(R.layout.fragment_cave_select_bottomsheet) {

    protected var toMoveSeedId: Int = 0

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCaveSelectBottomsheetBinding =
        FragmentCaveSelectBottomsheetBinding.inflate(inflater, container, false)

    abstract fun moveSeed()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moveSeed()
    }

    class Builder {
        fun build(toMoveSeedId: Int): CaveSelect {
            return CaveSelectBottomSheet().apply {
                this.toMoveSeedId = toMoveSeedId
            }
        }
    }
}
