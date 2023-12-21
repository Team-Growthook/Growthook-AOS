package com.growthook.aos.presentation.actionlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.growthook.aos.databinding.FragmentProceedingActionlistBinding
import com.growthook.aos.util.base.BaseFragment

class ProceedingActionlistFragment : BaseFragment<FragmentProceedingActionlistBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentProceedingActionlistBinding =
        FragmentProceedingActionlistBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
