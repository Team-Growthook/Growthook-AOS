package com.growthook.aos.presentation.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.growthook.aos.data.service.KakaoAuthService
import com.growthook.aos.databinding.FragmentMypageBinding
import com.growthook.aos.presentation.mypage.detailinfo.DetailMyPageActivity
import com.growthook.aos.presentation.onboarding.OnboardingActivity
import com.growthook.aos.util.GlideApp
import com.growthook.aos.util.base.BaseFragment
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
        setProfileImage()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUsedThook()
        viewModel.getGatherdThook()
    }

    private fun setProfileImage() {
        viewModel.profileUrl.observe(viewLifecycleOwner) { imageUrl ->
            if (imageUrl != null) {
                GlideApp.with(this).load(imageUrl).into(binding.ivMyPageUser)
            }
        }
    }

    private fun setNickName() {
        viewModel.nickName.observe(viewLifecycleOwner) {
            binding.tvMyPageNickname.text = it
        }
    }

    private fun clickDetailMyInfo() {
        binding.btnMyPageMyInfo.setOnClickListener {
            val intent = Intent(requireActivity(), DetailMyPageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickLogout() {
        binding.btnMyPageLogout.setOnClickListener {
            kakaoAuthService.kakaoLogout(viewModel.kakaoLogoutCallback)
            observeLogoutSuccess()
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

    private fun clickPolicy() {
        binding.btnMyPagePolicy.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://groovy-need-069.notion.site/9edc8ab432d34da682b9320f9bc6fd31"),
            )
            startActivity(intent)
        }
    }
}
