package com.growthook.aos.presentation.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentSelectMenuBottomsheetBinding
import com.growthook.aos.util.base.BaseBottomSheetFragment

class SelectMenuBottomSheet() :
    BaseBottomSheetFragment<FragmentSelectMenuBottomsheetBinding>(R.layout.fragment_select_menu_bottomsheet) {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var caveSelectBottomSheet: CaveSelectBottomSheet

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSelectMenuBottomsheetBinding =
        FragmentSelectMenuBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDisableDim()
        caveSelectBottomSheet = CaveSelectBottomSheet()

        binding.btnHomeSelectMove.setOnClickListener {
            caveSelectBottomSheet.show(parentFragmentManager, "show")
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
