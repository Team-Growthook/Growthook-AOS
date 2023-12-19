package com.growthook.aos.util.writingbottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.growthook.aos.databinding.FragmentLargeWritingBottomsheetBinding
import com.growthook.aos.util.base.BaseWritingBottomSheet
import com.growthook.aos.util.extension.CommonTextWatcher
import timber.log.Timber

class LargeWritingBottomSheet : BaseWritingBottomSheet() {

    private val binding: FragmentLargeWritingBottomsheetBinding
        get() = requireNotNull(_binding as FragmentLargeWritingBottomsheetBinding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLargeWritingBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickCloseBtn()
        observeTextInput()
    }

    override fun setClickSave(action: (String) -> Unit) {
        binding.btnLargeWritingSave.setOnClickListener {
            clickSaveBtn(binding.edtLargeWriting.text.toString())
        }
    }

    override fun setTitle() {
        Timber.d("타이틀 $title")
        binding.tvLargeWritingTitle.text = title
    }

    private fun clickCloseBtn() {
        binding.ibLargeWritingClose.setOnClickListener {
            dismiss()
        }
    }

    private fun observeTextInput() {
        val textWatcher = CommonTextWatcher(onChanged = { _, _, _, _ ->
            binding.btnLargeWritingSave.isEnabled = true
        })

        binding.edtLargeWriting.addTextChangedListener(textWatcher)
    }
}
