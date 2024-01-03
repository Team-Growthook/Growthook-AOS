package com.growthook.aos.presentation.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.growthook.aos.databinding.FragmentDescBinding
import com.growthook.aos.util.base.BaseFragment

class DescFragment : BaseFragment<FragmentDescBinding>() {

    private lateinit var callback: OnBackPressedCallback

    private var _adapter: DescAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { "adapter is null" }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentDescBinding = FragmentDescBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@DescFragment, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _adapter = DescAdapter()
        binding.vpDescExplain.adapter = adapter

        TabLayoutMediator(binding.tlDescIndicator, binding.vpDescExplain) { tab, position ->
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }
    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}
