package com.growthook.aos.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.growthook.aos.data.service.KakaoAuthService
import com.growthook.aos.databinding.ActivityLoginBinding
import com.growthook.aos.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var kakaoAuthService: KakaoAuthService

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isAlreadyLogin()
        startKakaoLogin()
        isKakaoLogin()
    }

    private fun startKakaoLogin() {
        binding.btnLoginKakao.setOnClickListener {
            kakaoAuthService.startKakaoLogin(viewModel.kakaoLoginCallback)
        }
    }

    private fun getUserKakaoNickName() {
        kakaoAuthService.kakaoGetUserInfo(viewModel.kakaoUserCallback)
    }

    private fun isKakaoLogin() {
        viewModel.isKakaoLogin.observe(this) { isLogin ->
            if (isLogin) {
                getUserKakaoNickName()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun isAlreadyLogin() {
        viewModel.checkIsAlreadyLogin()
        viewModel.isAlreadyLogin.observe(this) { isAlreadyLogin ->
            if (isAlreadyLogin) {
                val intent =
                    Intent(this@LoginActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
    }
}
