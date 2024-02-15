package com.growthook.aos.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.growthook.aos.App
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityMainBinding
import com.growthook.aos.presentation.actionlist.ActionlistFragment
import com.growthook.aos.presentation.home.HomeFragment
import com.growthook.aos.presentation.mypage.MyPageFragment
import com.growthook.aos.presentation.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
        isAlreadyLogin()
    }

    private fun initBottomNavigation() {
        binding.bnvHome.selectedItemId = R.id.menu_home
        supportFragmentManager.findFragmentById(R.id.fcv_home)
            ?: navigateTo<HomeFragment>()
        binding.bnvHome.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> navigateTo<HomeFragment>()
                R.id.menu_planlist -> {
                    navigateTo<ActionlistFragment>()
                     App.trackEvent("${viewModel.memberId.value} + 액션리스트 이동")
                }
                R.id.menu_mypage -> {
                    navigateTo<MyPageFragment>()
                    App.trackEvent("${viewModel.memberId.value} + 마이페이지 이동")
                }
            }
            true
        }
    }

    private fun isAlreadyLogin() {
        viewModel.isAlreadyLogin.observe(this) { isAlreadyLogin ->
            if (!isAlreadyLogin) {
                val intent =
                    Intent(this, OnboardingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_home, T::class.simpleName)
        }
    }
}
