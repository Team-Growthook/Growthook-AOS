package com.growthook.aos.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.growthook.aos.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "binding is null" }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTitleText()
        initAdapter()
    }

    private fun initTitleText() {
        viewModel.nickname.observe(viewLifecycleOwner) { nickName ->
            binding.tvHomeAppbarTitle.text = "${nickName}님의 동굴 속"
        }
        viewModel.insightAmount.observe(viewLifecycleOwner) { insightAmount ->
            binding.tvHomeInsightTitle.text = "${insightAmount}개의 씨앗을 모았어요!"
        }
    }

    private fun initAdapter() {
        val homeInsightAdapter = HomeInsightAdapter()
        homeInsightAdapter.submitList(viewModel.dummyInsights)
        binding.rvHomeInsight.adapter = homeInsightAdapter

        val caveAdapter = CaveAdapter()
        caveAdapter.submitList(viewModel.dummyCave)
        binding.rvHomeCave.adapter = caveAdapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
