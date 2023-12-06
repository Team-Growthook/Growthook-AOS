package com.growthook.aos.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentHomeBinding
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.presentation.MainActivity
import com.growthook.aos.presentation.cavedetail.CaveDetailActivity
import com.growthook.aos.presentation.insight.noactionplan.InsightMenuBottomsheet
import com.growthook.aos.presentation.insight.noactionplan.NoActionplanInsightActivity
import com.growthook.aos.util.EmptyDataObserver
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.base.BaseFragment
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonSizeSpec
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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
    }

    private fun setInsightAdapter() {
        _insightAdapter = HomeInsightAdapter(::selectedItem, ::clickedScrap)
        viewModel.insights.observe(viewLifecycleOwner) {
            insightAdapter.submitList(it)
            binding.tvHomeInsightTitle.text = "${it.size}개의 씨앗을 모았어요!"
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
        insightAdapter.registerAdapterDataObserver(
            EmptyDataObserver(
                binding.rvHomeInsight,
                binding.tvHomeEmptyInsight,
                binding.ivHomeEmptyInsight,
            ),
        )

        longTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()

                val selectedInsight = insightAdapter.getSelectedLongInsight()

                selectedInsight?.let {
                    activity.hideBottomNavigation(true)
                    selectMenuBottomSheet.show(parentFragmentManager, "show")
                    viewModel.longClickInsight.value = it
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
            BaseAlertDialog.Builder()
                .setCancelable(false)
                .build(
                    type = BaseAlertDialog.DialogType.RIGHT_INTENDED,
                    title = "잠금 해제하기",
                    description = "씨앗의 잠금을 해제하기 위해\n" +
                        "쑥 1개를 사용합니다.",
                    positiveText = "사용하기",
                    negativeText = "포기하기",
                    tipText = "Tip. 인사이트 ‘계획하기’를 통해 액션 플랜을 설정하고,\n" +
                        "이를 달성하면 새로운 쑥을 얻을 수 있어요!",
                    isBackgroundImageVisility = false,
                    isDescriptionVisility = true,
                    isRemainThookVisility = true,
                    isTipVisility = true,
                    negativeAction = {
                    },
                    positiveAction = {
                        Toast.makeText(context, "잠금이 영구적으로 해제되었어요!", Toast.LENGTH_SHORT).show()
                        val intent =
                            Intent(requireActivity(), NoActionplanInsightActivity::class.java)
                        intent.putExtra("insightId", item.insightId)
                        startActivity(intent)
                    },
                ).show(parentFragmentManager, InsightMenuBottomsheet.DELETE_DIALOG)
        } else if (!item.isAction) {
            val intent =
                Intent(requireActivity(), NoActionplanInsightActivity::class.java)
            intent.putExtra("insightId", item.insightId)
            startActivity(intent)
        }
    }

    private fun setCaveAdapter() {
        _caveAdapter = CaveAdapter(::clickedCave)
        viewModel.caves.observe(viewLifecycleOwner) {
            Timber.d("리사이클러뷰 동굴 개수 ${it.size}")
            caveAdapter.submitList(it)
        }
        binding.rvHomeCave.adapter = caveAdapter
        caveAdapter.registerAdapterDataObserver(
            EmptyDataObserver(
                binding.rvHomeCave,
                binding.tvHomeEmptyCave,
            ),
        )
    }

    private fun clickedCave(item: Cave) {
        val intent = Intent(requireActivity(), CaveDetailActivity::class.java)
        intent.putExtra("caveId", item.id)
        startActivity(intent)
    }

    private fun clickedScrap(isScrap: Boolean) {
        viewModel.changeScrap(isScrap)
        Timber.d("스크랩 $isScrap")
        Toast.makeText(requireContext(), "스크랩 완료", Toast.LENGTH_SHORT).show()
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

        val insightCountView = yesAlertBalloon.getContentView()
            .findViewById<TextView>(R.id.tv_home_alert_insight_count)

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
                binding.ibHomeAlert.setImageResource(R.drawable.ic_home_no_alert)
                binding.ibHomeAlert.setOnClickListener {
                    noAlertBalloon.showAlignBottom(it)
                    noAlertBalloon.dismiss()
                }
            } else if (alertAmount >= 1) {
                insightCountView.text = "${alertAmount}개"
                binding.ibHomeAlert.setImageResource(R.drawable.ic_home_yes_alert)
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
