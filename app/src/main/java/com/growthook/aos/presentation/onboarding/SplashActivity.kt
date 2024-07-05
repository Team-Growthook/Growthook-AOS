package com.growthook.aos.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.growthook.aos.databinding.ActivitySplashBinding
import com.growthook.aos.presentation.MainActivity
import com.growthook.aos.presentation.main.MainComposeActivity
import com.growthook.aos.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>({ ActivitySplashBinding.inflate(it) }) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity()
    }

    private fun startActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainComposeActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }

    companion object {
        private const val SPLASH_DELAY = 500L
    }
}
