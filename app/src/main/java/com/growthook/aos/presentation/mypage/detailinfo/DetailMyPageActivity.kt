package com.growthook.aos.presentation.mypage.detailinfo

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.growthook.aos.data.service.KakaoAuthService
import com.growthook.aos.databinding.ActivityDetailMyPageBinding
import com.growthook.aos.presentation.onboarding.LoginActivity
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.base.BaseAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailMyPageActivity :
    BaseActivity<ActivityDetailMyPageBinding>({ ActivityDetailMyPageBinding.inflate(it) }) {

    @Inject
    lateinit var kakaoAuthService: KakaoAuthService

    private val viewModel by viewModels<DetailMyPageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clickBackNavi()
        clickDeleteAccount()
    }

    private fun clickBackNavi() {
        binding.tbMyPageDetail.setNavigationOnClickListener {
            finish()
        }
    }

    private fun clickDeleteAccount() {
        binding.tvDetailMyPageDeleteAccount.setOnClickListener {
            BaseAlertDialog.Builder()
                .setCancelable(false)
                .build(
                    type = BaseAlertDialog.DialogType.LEFT_INTENDED,
                    title = "정말 탈퇴 하시겠어요?",
                    description = "탈퇴 시, 수집한 인사이트와\n" +
                        "달성한 액션플랜에 대한 정보가\n" +
                        "모두 없어져요.",
                    positiveText = "남아있기",
                    negativeText = "탈퇴하기",
                    tipText = "",
                    isBackgroundImageVisility = false,
                    isDescriptionVisility = true,
                    isRemainThookVisility = false,
                    isTipVisility = false,
                    negativeAction = {
                        deleteAccount()
                    },
                    positiveAction = {
                    },
                ).show(supportFragmentManager, "delete account")
        }
    }

    private fun deleteAccount() {
        kakaoAuthService.kakaoDeleteAccount(viewModel.kakaoCallback)
        viewModel.isKakaoDeleteAccount.observe(this) {
            if (it) {
                val intent =
                    Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}
