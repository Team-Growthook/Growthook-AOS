package com.growthook.aos.presentation.actionlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.growthook.aos.databinding.FragmentCompletedActionlistBinding
import com.growthook.aos.util.base.BaseFragment

class CompletedActionlistFragment : BaseFragment<FragmentCompletedActionlistBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCompletedActionlistBinding =
        FragmentCompletedActionlistBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
