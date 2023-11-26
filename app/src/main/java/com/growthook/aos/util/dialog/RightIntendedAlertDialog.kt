package com.growthook.aos.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.growthook.aos.databinding.DialogRightIntendedBinding
import com.growthook.aos.util.base.BaseAlertDialog

class RightIntendedAlertDialog : BaseAlertDialog() {
    private val binding: DialogRightIntendedBinding get() = _binding!! as DialogRightIntendedBinding

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
        binding.tvRightIntendedTip.text = tipText
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
