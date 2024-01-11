package com.growthook.aos.util.dialog

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.growthook.aos.R
import com.growthook.aos.databinding.DialogRightIntendedBinding
import com.growthook.aos.util.base.BaseAlertDialog

class RightIntendedAlertDialog : BaseAlertDialog() {
    private val binding: DialogRightIntendedBinding
        get() = requireNotNull(_binding as DialogRightIntendedBinding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogRightIntendedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setTitle() {
        binding.tvRightIntendedTitle.text = title
    }

    override fun setDescription() {
        binding.tvRightIntendedDesc.text = description
    }

    override fun setPositiveText() {
        binding.tvRightIntendedRight.text = positiveText
    }

    override fun setNegativeText() {
        binding.tvRightIntendedLeft.text = negativeText
    }

    override fun setTipText() {
        binding.tvRightIntendedTip.apply {
            text = modifyTextColor(
                tipText.toString(),
                "Tip.",
                ContextCompat.getColor(requireContext(), R.color.MainGreen400),
            )
        }
    }

    private fun modifyTextColor(text: String, targetWord: String, color: Int): SpannableString {
        val spannable = SpannableString(text)
        val startIndex = text.indexOf(targetWord)
        if (startIndex != -1) {
            spannable.setSpan(
                ForegroundColorSpan(color),
                startIndex,
                startIndex + targetWord.length,
                0,
            )
        }
        return spannable
    }

    override fun setTipVisility() {
        if (isTipVisility == true) {
            binding.tvRightIntendedTip.visibility = View.VISIBLE
        } else {
            binding.tvRightIntendedTip.visibility = View.GONE
        }
    }

    override fun remainThookVisility() {
        if (isRemainThookVisility == true) {
            binding.clRightIntendedRemainThook.visibility = View.VISIBLE
        } else {
            binding.clRightIntendedRemainThook.visibility = View.GONE
        }
    }

    override fun backgroundImageVisility() {
    }

    override fun descriptionVisility() {
    }

    override fun setPositiveClick(action: () -> Unit) {
        binding.tvRightIntendedRight.setOnClickListener { action() }
    }

    override fun setNegativeClick(action: () -> Unit) {
        binding.tvRightIntendedLeft.setOnClickListener { action() }
    }
}
