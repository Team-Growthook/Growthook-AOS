package com.growthook.aos.presentation.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.growthook.aos.App
import com.growthook.aos.data.service.KakaoAuthService
import com.growthook.aos.databinding.FragmentMypageBinding
import com.growthook.aos.presentation.MainActivity
import com.growthook.aos.presentation.MainActivity.Companion.USER_ID
import com.growthook.aos.presentation.mypage.detailinfo.DetailMyPageActivity
import com.growthook.aos.presentation.onboarding.OnboardingActivity
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.base.BaseFragment
import com.growthook.aos.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>() {

    @Inject
    lateinit var kakaoAuthService: KakaoAuthService

    private val viewModel: MyPageViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentMypageBinding = FragmentMypageBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNickName()
        setGatherdThook()
        setUsedThook()
        clickDetailMyInfo()
        clickLogout()
        clickPolicy()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUsedThook()
        viewModel.getGatherdThook()
    }

    private fun setNickName() {
        viewModel.nickName.observe(viewLifecycleOwner) {
            binding.tvMyPageNickname.text = it
        }
    }

    private fun clickDetailMyInfo() {
        binding.btnMyPageMyInfo.setOnSingleClickListener {
            val intent = Intent(requireActivity(), DetailMyPageActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION)
            App.trackEvent("$USER_ID + 내 정보 보기")
            startActivity(intent)
        }
    }

    private fun clickLogout() {
        binding.btnMyPageLogout.setOnClickListener {
            BaseAlertDialog.Builder()
                .setCancelable(false)
                .build(
                    type = BaseAlertDialog.DialogType.LEFT_INTENDED,
                    title = "로그아웃 하시겠습니까?",
                    description = "",
                    positiveText = "취소",
                    negativeText = "로그아웃",
                    tipText = "",
                    isBackgroundImageVisility = false,
                    isDescriptionVisility = false,
                    isRemainThookVisility = false,
                    isTipVisility = false,
                    negativeAction = {
                        kakaoAuthService.kakaoLogout(viewModel.kakaoLogoutCallback)
                        observeLogoutSuccess()
                    },
                    positiveAction = {
                    },
                    remainThookText = "",
                ).show(parentFragmentManager, "logout")
        }
    }

    private fun observeLogoutSuccess() {
        viewModel.isLogoutSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                val intent = Intent(requireActivity(), OnboardingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    private fun setGatherdThook() {
        viewModel.gatherdThook.observe(viewLifecycleOwner) { thookCount ->
            binding.tvMyPageHarvestThookCount.text = thookCount.toString()
        }
    }

    private fun setUsedThook() {
        viewModel.usedThook.observe(viewLifecycleOwner) { thookCount ->
            binding.tvMyPageUsedThookCount.text = thookCount.toString()
        }
    }

    private fun clickInstruction() {
        binding.btnMyPageInstructions.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.notion.so/a6ac706599224bbbb9f7a6b449c1a02f?pvs=4"),
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION)
            App.trackEvent("${MainActivity.USER_ID} + 사용법 보기")
            startActivity(intent)
        }
    }

    private fun clickNotice() {
        binding.btnMyPageNotice.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.notion.so/9bba9068c49e42d98e0d9b5bd59674c9?pvs=4"),
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION)
            App.trackEvent("${MainActivity.USER_ID} + 공지사항 보기")
            startActivity(intent)
        }
    }

    private fun clickFAQ() {
        binding.btnMyPageFaq.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.notion.so/6cdc4f9f7f38490084a89da1bfa083ab?pvs=4"),
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION)
            App.trackEvent("${MainActivity.USER_ID} + 자주 묻는 질문 보기")
            startActivity(intent)
        }
    }

    private fun clickPolicy() {
        binding.btnMyPagePolicy.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                // Uri.parse("https://groovy-need-069.notion.site/9edc8ab432d34da682b9320f9bc6fd31"),
                Uri.parse("https://www.notion.so/9edc8ab432d34da682b9320f9bc6fd31?pvs=4"),
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION)
            App.trackEvent("$USER_ID + 약관 및 정책 보기")
            startActivity(intent)
        }
    }
}
