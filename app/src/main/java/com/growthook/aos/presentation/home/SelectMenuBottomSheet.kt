package com.growthook.aos.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentSelectMenuBottomsheetBinding
import com.growthook.aos.util.base.BaseBottomSheetFragment

class SelectMenuBottomSheet :
    BaseBottomSheetFragment<FragmentSelectMenuBottomsheetBinding>(R.layout.fragment_select_menu_bottomsheet) {

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            Toast.makeText(context, "뒤로가기", Toast.LENGTH_SHORT).show()

            isEnabled = false
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSelectMenuBottomsheetBinding =
        FragmentSelectMenuBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            dialog?.let { dialog ->
                dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
