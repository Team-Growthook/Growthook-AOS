package com.growthook.aos.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentHomeBinding
import com.growthook.aos.util.base.BaseFragment
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonSizeSpec
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private var _caveAdapter: CaveAdapter? = null
    private val caveAdapter
        get() = requireNotNull(_caveAdapter) { "adapter is null" }

    private var _insightAdapter: HomeInsightAdapter? = null
    private val insightAdapter
        get() = requireNotNull(_insightAdapter) { "adapter is null" }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitleText()
        setInsightAdapter()
        setAlertMessage()
        clickScrap()
        setCaveAdapter()
    }

    private fun setTitleText() {
        viewModel.nickname.observe(viewLifecycleOwner) { nickName ->
            binding.tvHomeAppbarTitle.text = "${nickName}님의 동굴 속"
        }
        viewModel.insightAmount.observe(viewLifecycleOwner) { insightAmount ->
            binding.tvHomeInsightTitle.text = "${insightAmount}개의 씨앗을 모았어요!"
        }
    }

    private fun setInsightAdapter() {
        _insightAdapter = HomeInsightAdapter()
        viewModel.insights.observe(viewLifecycleOwner) {
            insightAdapter.submitList(it)
        }
        binding.rvHomeInsight.adapter = insightAdapter

        val longTracker = SelectionTracker.Builder<Long>(
            "mySelection",
            binding.rvHomeInsight,
            StableIdKeyProvider(binding.rvHomeInsight),
            HomeInsightAdapter.InsightDetailsLookup(binding.rvHomeInsight),
            StorageStrategy.createLongStorage(),
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything(),
        ).build()
        insightAdapter.setSelectionLongTracker(longTracker)

        longTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()

                val selectedInsight = insightAdapter.getSelectedLongInsight()
                Timber.d("선택된 insight: $selectedInsight")
            }
        })
    }

    private fun setCaveAdapter() {
        _caveAdapter = CaveAdapter()
        viewModel.caves.observe(viewLifecycleOwner) {
            caveAdapter.submitList(it)
        }
        binding.rvHomeCave.adapter = caveAdapter
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

    private fun clickScrap() {
        binding.chbHomeScrap.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                viewModel.getScrapedInsight()
            } else {
                viewModel.getInsights()
            }
        }
    }

    override fun onDestroyView() {
        _caveAdapter = null
        _insightAdapter = null
        super.onDestroyView()
    }
}
