package com.growthook.aos.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentWhatDifficultBinding
import com.growthook.aos.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WhatDifficultFragment : BaseFragment<FragmentWhatDifficultBinding>() {

    private val viewModel by viewModels<WhatDifficultViewModel>()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentWhatDifficultBinding =
        FragmentWhatDifficultBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickForget()
        clickNotFind()
        clickNotPractice()
        clickNotGrow()
        checkBtnEnable()
        binding.btnWhatDifficultForgetInsight.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.anim_slide_in_from_right_fade_in,
                R.anim.anim_fade_out,
                R.anim.anim_slide_in_from_left_fade_in,
                R.anim.anim_fade_out,
            ).replace(R.id.fcv_onboarding_main, SolutionFragment()).addToBackStack(null).commit()
        }
    }

    private fun clickForget() {
        binding.clWhatDifficultForget.setOnClickListener {
            viewModel.changeForget()
        }
        viewModel.forget.observe(this) {
            with(binding) {
                changeColor(it, tvWhatDifficultForget, clWhatDifficultForget)
            }
        }
    }

    private fun clickNotFind() {
        binding.clWhatDifficultNotFind.setOnClickListener {
            viewModel.changeNotFind()
        }
        viewModel.notFind.observe(this) {
            with(binding) {
                changeColor(it, tvWhatDifficultNotFind, clWhatDifficultNotFind)
            }
        }
    }

    private fun clickNotPractice() {
        binding.clWhatDifficultNotPractice.setOnClickListener {
            viewModel.changeNotPractice()
        }
        viewModel.notPractice.observe(this) {
            with(binding) {
                changeColor(it, tvWhatDifficultNotPractice, clWhatDifficultNotPractice)
            }
        }
    }

    private fun clickNotGrow() {
        binding.clWhatDifficultNotGrow.setOnClickListener {
            viewModel.changeNotGrow()
        }

        viewModel.notGrow.observe(this) {
            with(binding) {
                changeColor(it, tvWhatDifficultNotGrow, clWhatDifficultNotGrow)
            }
        }
    }

    private fun changeColor(click: Boolean, text: TextView, cl: ViewGroup) {
        if (click) {
            text.setTextColor(requireContext().getColor(R.color.Green200))
            cl.setBackgroundResource(R.drawable.rect_gray500_line_10)
        } else {
            text.setTextColor(requireContext().getColor(R.color.White000))
            cl.setBackgroundResource(R.drawable.rect_gray400_fill_10)
        }
    }

    private fun checkBtnEnable() {
        viewModel.isBtnEnable.observe(this) { isEnable ->
            binding.btnWhatDifficultForgetInsight.isEnabled = isEnable
        }
    }
}
