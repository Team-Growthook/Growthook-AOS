package com.growthook.aos.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.growthook.aos.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        initBottomNavigation()
//        isAlreadyLogin()
    }

//    private fun initBottomNavigation() {
//        binding.bnvHome.selectedItemId = R.id.menu_home
//        supportFragmentManager.findFragmentById(R.id.fcv_home)
//            ?: navigateTo<HomeFragment>()
//        binding.bnvHome.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.menu_home -> navigateTo<HomeFragment>()
//                R.id.menu_planlist -> {
//                    navigateTo<TodolistFragment>()
//                    App.trackEvent("${viewModel.memberId.value} + 액션리스트 이동")
//                }
//
//                R.id.menu_mypage -> {
//                    navigateTo<MyPageFragment>()
//                    App.trackEvent("${viewModel.memberId.value} + 마이페이지 이동")
//                }
//            }
//            true
//        }
//    }

//    private fun isAlreadyLogin() {
//        viewModel.isAlreadyLogin.observe(this) { isAlreadyLogin ->
//            if (!isAlreadyLogin) {
//                val intent =
//                    Intent(this, OnboardingActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent)
//                finish()
//            }
//
//            USER_ID = viewModel.memberId.value ?: 0
//
//            if (USER_ID != 0) {
//                App.trackEvent("$USER_ID + 앱 실행")
//            }
//        }
//    }

//    private inline fun <reified T : Fragment> navigateTo() {
//        supportFragmentManager.commit {
//            replace<T>(R.id.fcv_home, T::class.simpleName)
//        }
//    }
//
//    override fun onUserLeaveHint() {
//        super.onUserLeaveHint()
//
//        if (USER_ID != 0) {
//            App.trackEvent("${viewModel.memberId.value} + 나가기")
//            Timber.d("amplitude 나가기 leave")
//        }
//    }
//
//    override fun onDestroy() {
//        if (USER_ID != 0) {
//            App.trackEvent("${viewModel.memberId.value} + 나가기")
//            Timber.d("amplitude 나가기 destroy")
//        }
//        super.onDestroy()
//    }
//
//    companion object {
//        var USER_ID = 0
//    }
}
