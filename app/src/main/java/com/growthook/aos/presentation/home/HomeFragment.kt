package com.growthook.aos.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentHomeBinding
import com.growthook.aos.domain.entity.Insight
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonSizeSpec
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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

        setTitleText()
        setAdapter()
        setAlertMessage()

        binding.chbHomeScrap.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                viewModel.getScrapedInsight()
            } else {
                viewModel.getInsights()
            }
        }

        if (!binding.chbHomeScrap.isChecked) {
            viewModel.getInsights()
        } else {
            viewModel.getScrapedInsight()
        }
    }

    private fun setTitleText() {
        viewModel.nickname.observe(viewLifecycleOwner) { nickName ->
            binding.tvHomeAppbarTitle.text = "${nickName}님의 동굴 속"
        }
        viewModel.insightAmount.observe(viewLifecycleOwner) { insightAmount ->
            binding.tvHomeInsightTitle.text = "${insightAmount}개의 씨앗을 모았어요!"
        }
    }

    private fun setAdapter() {
        val homeInsightAdapter = HomeInsightAdapter(::clickedInsight)
        viewModel.insights.observe(viewLifecycleOwner) {
            homeInsightAdapter.submitList(it)
        }
        binding.rvHomeInsight.adapter = homeInsightAdapter

        val tracker = SelectionTracker.Builder<Long>(
            "mySelection",
            binding.rvHomeInsight,
            StableIdKeyProvider(binding.rvHomeInsight),
            HomeInsightAdapter.InsightDetailsLookup(binding.rvHomeInsight),
            StorageStrategy.createLongStorage(),
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything(),
        ).build()
        homeInsightAdapter.setSelectionTracker(tracker)

        val caveAdapter = CaveAdapter()
        viewModel.caves.observe(viewLifecycleOwner) {
            caveAdapter.submitList(it)
        }
        binding.rvHomeCave.adapter = caveAdapter
    }

    private fun clickedInsight(insight: Insight) {
        Timber.d("클릭된 insight $insight")
    }

    private fun setAlertMessage() {
        val yesAlertBalloon = Balloon.Builder(requireContext())
            .setLayout(R.layout.item_home_yes_alert)
            .setIsVisibleArrow(false)
            .setWidth(BalloonSizeSpec.WRAP)
            .setHeight(BalloonSizeSpec.WRAP)
            .setMarginRight(13)
            .setLifecycleOwner(viewLifecycleOwner)
            .setAutoDismissDuration(2000L)
            .build()

        val noAlertBalloon = Balloon.Builder(requireContext())
            .setLayout(R.layout.item_home_no_alert)
            .setIsVisibleArrow(false)
            .setWidth(BalloonSizeSpec.WRAP)
            .setHeight(BalloonSizeSpec.WRAP)
            .setLifecycleOwner(viewLifecycleOwner)
            .setMarginRight(13)
            .setAutoDismissDuration(3000L)
            .build()

        viewModel.alertAmount.observe(viewLifecycleOwner) { alertAmount ->
            if (alertAmount == 0) {
                binding.ibHomeAlert.setOnClickListener {
                    noAlertBalloon.showAlignBottom(it)
                    noAlertBalloon.dismiss()
                }
            } else if (alertAmount >= 1) {
                binding.ibHomeAlert.setOnClickListener {
                    yesAlertBalloon.showAlignBottom(it)
                    yesAlertBalloon.dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
