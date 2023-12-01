package com.growthook.aos.presentation.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentHomeBinding
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.presentation.MainActivity
import com.growthook.aos.presentation.insight.noactionplan.NoActionplanInsightActivity
import com.growthook.aos.util.base.BaseFragment
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonSizeSpec
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var selectMenuBottomSheet: SelectMenuBottomSheet
    private lateinit var activity: MainActivity

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

        selectMenuBottomSheet = SelectMenuBottomSheet()
        activity = requireActivity() as MainActivity
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
        _insightAdapter = HomeInsightAdapter(::selectedItem)
        viewModel.insights.observe(viewLifecycleOwner) {
            insightAdapter.submitList(it)
        }
        binding.rvHomeInsight.adapter = insightAdapter

        val longTracker = SelectionTracker.Builder<Long>(
            "myLongSelection",
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

                selectedInsight?.let {
                    activity.hideBottomNavigation(true)

                    selectMenuBottomSheet.show(parentFragmentManager, "show")
                }
            }
        })

        viewModel.isMenuDismissed.observe(viewLifecycleOwner) {
            longTracker.clearSelection()
            activity.hideBottomNavigation(false)
        }
    }

    private fun selectedItem(item: Insight) {
        if (item.isLocked) {
            Toast.makeText(context, "잠금 해제하기", Toast.LENGTH_SHORT).show()
        } else if (!item.isAction) {
            val intent =
                Intent(requireActivity(), NoActionplanInsightActivity::class.java)
            intent.putExtra("insightId", item.insightId)
            startActivity(intent)
        }
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
            .setAutoDismissDuration(2000L)
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
