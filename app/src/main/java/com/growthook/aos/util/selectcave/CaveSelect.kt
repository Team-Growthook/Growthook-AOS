package com.growthook.aos.util.selectcave

import android.view.LayoutInflater
import android.view.ViewGroup
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentCaveSelectBottomsheetBinding
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.util.base.BaseBottomSheetFragment

abstract class CaveSelect :
    BaseBottomSheetFragment<FragmentCaveSelectBottomsheetBinding>(R.layout.fragment_cave_select_bottomsheet) {

    protected var toMoveSeedId: Int = 0
    protected lateinit var clickBtnAction: (Cave) -> Unit

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCaveSelectBottomsheetBinding =
        FragmentCaveSelectBottomsheetBinding.inflate(inflater, container, false)

    class Builder {
        fun build(
            type: CaveSelectType,
            toMoveSeedId: Int,
            clickBtnAction: (Cave) -> Unit = {},
        ): CaveSelect {
            return type.getInstance().apply {
                this.toMoveSeedId = toMoveSeedId
                this.clickBtnAction = clickBtnAction
            }
        }
    }

    enum class CaveSelectType {
        YES_API {
            override fun getInstance(): CaveSelect = YesApiCaveSelectBottomSheet()
        },
        NO_API {
            override fun getInstance(): CaveSelect = NoApiCaveSelectBottomSheet()
        }, ;

        abstract fun getInstance(): CaveSelect
    }
}
