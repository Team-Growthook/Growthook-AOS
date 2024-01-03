package com.growthook.aos.presentation.onboarding

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityWhatDifficultBinding
import com.growthook.aos.util.base.BaseActivity

class WhatDifficultActivity :
    BaseActivity<ActivityWhatDifficultBinding>({ ActivityWhatDifficultBinding.inflate(it) }) {

    private val viewModel by viewModels<WhatDifficultViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clickForget()
        clickNotFind()
        clickNotPractice()
        clickNotGrow()
        checkBtnEnable()
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
            text.setTextColor(getColor(R.color.Green200))
            cl.setBackgroundResource(R.drawable.rect_gray500_line_10)
        } else {
            text.setTextColor(getColor(R.color.White000))
            cl.setBackgroundResource(R.drawable.rect_gray400_fill_10)
        }
    }

    private fun checkBtnEnable() {
        viewModel.isBtnEnable.observe(this) { isEnable ->
            binding.btnWhatDifficultForgetInsight.isEnabled = isEnable
        }
    }
}
