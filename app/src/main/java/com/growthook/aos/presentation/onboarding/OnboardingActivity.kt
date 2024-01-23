package com.growthook.aos.presentation.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityOnboardingBinding
import com.growthook.aos.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity :
    BaseActivity<ActivityOnboardingBinding>({ ActivityOnboardingBinding.inflate(it) }) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragment()
    }

    private fun initFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_onboarding_main)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_onboarding_main, WhatDifficultFragment()).commit()
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }
}
