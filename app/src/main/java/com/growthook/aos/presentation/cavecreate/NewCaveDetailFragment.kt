package com.growthook.aos.presentation.cavecreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.growthook.aos.databinding.FragmentNewCaveDetailBinding
import com.growthook.aos.util.base.BaseFragment

class NewCaveDetailFragment : BaseFragment<FragmentNewCaveDetailBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentNewCaveDetailBinding =
        FragmentNewCaveDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
