package com.growthook.aos.presentation.onboarding

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
}
