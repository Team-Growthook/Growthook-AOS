package com.growthook.aos.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.growthook.aos.data.service.KakaoAuthService
import com.growthook.aos.databinding.FragmentMypageBinding
import com.growthook.aos.presentation.onboarding.LoginActivity
import com.growthook.aos.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>() {

    @Inject
    lateinit var kakaoAuthService: KakaoAuthService

    private val viewModel: MyPageViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentMypageBinding = FragmentMypageBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickLogout()
    }

    private fun clickLogout() {
        binding.btnMyPageLogout.setOnClickListener {
            kakaoAuthService.kakaoLogout(viewModel.kakaoLogoutCallback)
            viewModel.isLogoutSuccess.observe(viewLifecycleOwner) {
                if (it) {
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }
}
