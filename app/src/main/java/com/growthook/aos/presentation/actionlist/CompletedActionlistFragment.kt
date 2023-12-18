package com.growthook.aos.presentation.actionlist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.growthook.aos.databinding.FragmentCompletedActionlistBinding
import com.growthook.aos.util.base.BaseFragment

class CompletedActionlistFragment : BaseFragment<FragmentCompletedActionlistBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCompletedActionlistBinding =
        FragmentCompletedActionlistBinding.inflate(inflater, container, false)
}
