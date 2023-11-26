package com.growthook.aos.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.growthook.aos.databinding.DialogLeftIntendedBinding
import com.growthook.aos.util.base.BaseAlertDialog

class LeftIntendedAlertDialog : BaseAlertDialog() {
    private val binding: DialogLeftIntendedBinding get() = _binding!! as DialogLeftIntendedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogLeftIntendedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setTitle() {
        binding.tvLeftIntendedTitle.text = title
    }

    override fun setDescription() {
        binding.tvLeftIntendedDesc.text = description
    }

    override fun setPositiveText() {
        binding.tvLeftIntendedLeft.text = positiveText
    }

    override fun setNegativeText() {
        binding.tvLeftIntendedRight.text = negativeText
    }

    override fun setTipText() {
    }

    override fun setTipVisility() {
    }

    override fun remainThookVisility() {
    }

    override fun backgroundImageVisility() {
    }

    override fun descriptionVisility() {
        if (isDescriptionVisility == true) {
            binding.tvLeftIntendedDesc.visibility = View.VISIBLE
        } else {
            binding.tvLeftIntendedDesc.visibility = View.GONE
        }
    }

    override fun setPositiveClick(action: () -> Unit) {
        binding.tvLeftIntendedLeft.setOnClickListener { action() }
    }

    override fun setNegativeClick(action: () -> Unit) {
        binding.tvLeftIntendedRight.setOnClickListener { action() }
    }
}
