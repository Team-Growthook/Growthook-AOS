package com.growthook.aos.util.dialog

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.growthook.aos.R
import com.growthook.aos.databinding.DialogSingleIntendedBinding
import com.growthook.aos.util.base.BaseAlertDialog

class SingleIntendedAlertDialog : BaseAlertDialog() {
    private val binding: DialogSingleIntendedBinding
        get() = requireNotNull(_binding as DialogSingleIntendedBinding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogSingleIntendedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setTitle() {
        binding.tvSingleIntendedTitle.text = title
    }

    override fun setDescription() {
        binding.tvSingleIntendedDesc.text = description
    }

    override fun setPositiveText() {
        binding.tvSingleIntendedBottom.text = positiveText
    }

    override fun setNegativeText() {
    }

    override fun setTipText() {
        binding.tvSingleIntendedTip.apply {
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

    override fun setRemainThookText() {
    }

    override fun setTipVisility() {
        if (isTipVisility == true) {
            binding.tvSingleIntendedTip.visibility = View.VISIBLE
        } else {
            binding.tvSingleIntendedTip.visibility = View.GONE
        }
    }

    override fun remainThookVisility() {
    }

    override fun backgroundImageVisility() {
        if (isBackgroundImageVisility == true) {
            binding.ivSingleIntendedRewardThook.visibility = View.VISIBLE
        } else {
            binding.ivSingleIntendedRewardThook.visibility = View.GONE
        }
    }

    override fun descriptionVisility() {
    }

    override fun setPositiveClick(action: () -> Unit) {
        binding.tvSingleIntendedBottom.setOnClickListener { action() }
    }

    override fun setNegativeClick(action: () -> Unit) {
    }
}
