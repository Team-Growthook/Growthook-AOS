package com.growthook.aos.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
