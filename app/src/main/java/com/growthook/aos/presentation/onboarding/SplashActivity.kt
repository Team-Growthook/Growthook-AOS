package com.growthook.aos.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.growthook.aos.databinding.ActivitySplashBinding
import com.growthook.aos.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>({ ActivitySplashBinding.inflate(it) }) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isOnboarding()
    }

    private fun isOnboarding() {
        viewModel.isOnboarding.observe(this) { isOnboarding ->
            if (isOnboarding) {
                startActivity(LoginActivity::class.java)
            } else {
                startActivity(OnboardingActivity::class.java)
            }
        }
    }

    private fun startActivity(toClass: Class<out AppCompatActivity>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, toClass)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }

    companion object {
        private const val SPLASH_DELAY = 500L
    }
}
