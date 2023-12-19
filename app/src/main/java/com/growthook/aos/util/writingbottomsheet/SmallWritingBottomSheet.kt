package com.growthook.aos.util.writingbottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.growthook.aos.databinding.FragmentSmallWritingBottomsheetBinding
import com.growthook.aos.util.base.BaseWritingBottomSheet
import com.growthook.aos.util.extension.CommonTextWatcher

class SmallWritingBottomSheet : BaseWritingBottomSheet() {

    private val binding: FragmentSmallWritingBottomsheetBinding
        get() = requireNotNull(_binding as FragmentSmallWritingBottomsheetBinding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSmallWritingBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickCloseBtn()
        observeTextInput()
    }

    private fun clickCloseBtn() {
        binding.ibSmallWritingClose.setOnClickListener {
            dismiss()
        }
    }

    private fun observeTextInput() {
        val textWatcher = CommonTextWatcher(onChanged = { _, _, _, _ ->
            binding.btnSmallWritingSave.isEnabled = true
        })

        binding.edtSmallWriting.addTextChangedListener(textWatcher)
    }

    override fun setClickSave(action: (String) -> Unit) {
        binding.btnSmallWritingSave.setOnClickListener {
            clickSaveBtn(binding.edtSmallWriting.text.toString())
        }
    }

    override fun setTitle() {
        binding.tvSmallWritingTitle.text = title
    }

    override fun setClickNoWrite(action: () -> Unit) {
    }
}
