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
        TODO("Not yet implemented")
    }

    override fun setDescription() {
        TODO("Not yet implemented")
    }

    override fun setPositiveText() {
        TODO("Not yet implemented")
    }

    override fun setNegativeText() {
        TODO("Not yet implemented")
    }

    override fun setPositiveClick(action: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun setNegativeClick(action: () -> Unit) {
        TODO("Not yet implemented")
    }
}
