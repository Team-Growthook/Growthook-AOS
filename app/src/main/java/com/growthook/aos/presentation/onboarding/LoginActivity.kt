package com.growthook.aos.presentation.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityOnboardingBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_onboarding)
    }
}
