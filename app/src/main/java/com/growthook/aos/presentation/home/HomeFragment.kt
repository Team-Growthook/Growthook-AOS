package com.growthook.aos.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.res.integerArrayResource
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.growthook.aos.App
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentHomeBinding
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.presentation.MainActivity
import com.growthook.aos.presentation.cavecreate.CreateNewCaveActivity
import com.growthook.aos.presentation.cavedetail.CaveDetailActivity
import com.growthook.aos.presentation.insight.actionplan.ActionplanInsightActivity
import com.growthook.aos.presentation.insight.noactionplan.InsightMenuBottomsheet
import com.growthook.aos.presentation.insight.noactionplan.NoActionplanInsightActivity
import com.growthook.aos.presentation.insight.write.InsightWriteActivity
import com.growthook.aos.util.EmptyDataObserver
import com.growthook.aos.util.EventObserver
import com.growthook.aos.util.LinearLayoutManagerWrapper
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.base.BaseFragment
import com.growthook.aos.util.extension.setOnSingleClickListener
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
        setThook()
        setInsightAdapter()
        setAlertMessage()
        clickScrap()
        setCaveAdapter()

        selectMenuBottomSheet = SelectMenuBottomSheet()
        activity = requireActivity() as MainActivity

        clickAddCave()
        clickAddSeedBtn()
        observeNickName()

        observeInsights()

        isInsightDelete()
    }

    private fun observeInsights() {
        viewModel.scrapedInsights.observe(viewLifecycleOwner) { insights ->
            insightAdapter.submitList(insights)
        }
        viewModel.unScrapedInsights.observe(viewLifecycleOwner) { insights ->
            insightAdapter.submitList(insights)
            if (insights.isNotEmpty()) {
                binding.tvHomeInsightTitle.text = "${insights.size}개의 씨앗을 모았어요"
            } else {
                binding.chbHomeScrap.isClickable = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        renewalData()
    }

    private fun clickAddCave() {
        binding.ivHomeAddCave.setOnSingleClickListener {
            val intent = Intent(requireActivity(), CreateNewCaveActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION)
            App.trackEvent("${viewModel.memberId.value} + 동굴 짓기")
            startActivity(intent)
        }
    }

    private fun setTitleText() {
        viewModel.setNickName()
    }

    private fun observeNickName() {
        viewModel.nickname.observe(viewLifecycleOwner) { nickName ->
            binding.tvHomeAppbarTitle.text = "${nickName}님의 동굴 속"
        }
    }

    private fun renewalData() {
        viewModel.getCaves()
        viewModel.getInsights()
        viewModel.getGatherdThook()
    }

    private fun setInsightAdapter() {
        _insightAdapter = HomeInsightAdapter(::selectedItem, ::clickedScrap)

        binding.rcvHomeInsight.adapter = insightAdapter
        binding.rcvHomeInsight.layoutManager = LinearLayoutManagerWrapper(requireActivity())

        observeListIsEmpty()
        setInsightTracker()
    }

    private fun observeListIsEmpty() {
        insightAdapter.registerAdapterDataObserver(
            EmptyDataObserver(
                binding.rcvHomeInsight,
                binding.tvHomeEmptyInsight,
                binding.ivHomeEmptyInsight,
            ),
        )
    }

    private fun setInsightTracker() {
        val longTracker = SelectionTracker.Builder<Long>(
            "myLongSelection",
            binding.rcvHomeInsight,
            StableIdKeyProvider(binding.rcvHomeInsight),
            HomeInsightAdapter.InsightDetailsLookup(binding.rcvHomeInsight),
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
                    binding.fabHomeAddInsight.visibility = View.GONE
                    selectMenuBottomSheet.show(parentFragmentManager, "show")
                    viewModel.longClickInsight.value = it
                }
            }
        })

        viewModel.isMenuDismissed.observe(viewLifecycleOwner) {
            longTracker.clearSelection()
            binding.fabHomeAddInsight.visibility = View.VISIBLE
        }
    }

    private fun isInsightDelete() {
        viewModel.isDelete.observe(viewLifecycleOwner) { isDelete ->
            if (isDelete) {
                if (binding.chbHomeScrap.isChecked) {
                    viewModel.getScrapedInsight()
                } else {
                    viewModel.getInsights()
                }
            }
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
                        val thookCount = viewModel.gatherdThook.value ?:0
                        if (thookCount <= 0) {
                            Toast.makeText(requireContext(), "쑥이 없어 잠금을 해제할 수 없어요", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.unLockSeed(item.seedId)
                            viewModel.isUnlock.observe(viewLifecycleOwner) {
                                Toast.makeText(context, "잠금이 영구적으로 해제되었어요!", Toast.LENGTH_SHORT)
                                    .show()
                                if (item.hasActionPlan) {
                                    startActivity(
                                        ActionplanInsightActivity.getIntent(
                                            requireContext(),
                                            item.seedId,
                                            "HomeFragment",
                                        ).addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION),
                                    )
                                } else {
                                    startActivity(
                                        NoActionplanInsightActivity.getIntent(
                                            requireContext(),
                                            item.seedId,
                                        ).addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION),
                                    )
                                }
                                viewModel.getGatherdThook()
                                viewModel.getAlertCount()
                            }
                        }
                    },
                    remainThookText = viewModel.gatherdThook.value.toString(),
                ).show(parentFragmentManager, InsightMenuBottomsheet.DELETE_DIALOG)
        } else if (item.hasActionPlan) {
            startActivity(
                ActionplanInsightActivity.getIntent(
                    requireContext(),
                    item.seedId,
                    "HomeFragment",
                ).addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION),
            )
        } else {
            startActivity(NoActionplanInsightActivity.getIntent(requireContext(), item.seedId)
                .addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION))
        }
    }

    private fun setCaveAdapter() {
        _caveAdapter = CaveAdapter(::clickedCave)
        viewModel.caves.observe(viewLifecycleOwner) {
            Timber.d("리사이클러뷰 동굴 개수 ${it.size}")
            caveAdapter.submitList(it)
        }
        binding.rcvHomeCave.adapter = caveAdapter
        caveAdapter.registerAdapterDataObserver(
            EmptyDataObserver(
                recyclerView = binding.rcvHomeCave,
                emptyViews = arrayOf(binding.tvHomeEmptyCave),
            ),
        )
    }

    private fun clickedCave(item: Cave) {
        startActivity(CaveDetailActivity.getIntent(requireContext(), item.id)
            .addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION))
    }

    private fun clickedScrap(seed: Insight) {
        viewModel.changeScrap(seed.seedId)
        observeScrap()
        notifyScrap(seed.isScraped)
    }

    private fun observeScrap() {
        viewModel.isScrapedSuccess.observe(
            viewLifecycleOwner,
            EventObserver { isSuccess ->
                if (isSuccess) {
                    if (binding.chbHomeScrap.isChecked) {
                        viewModel.getScrapedInsight()
                    } else {
                        viewModel.getInsights()
                    }
                }
            },
        )
    }

    private fun notifyScrap(isScraped: Boolean) {
        if (!isScraped) {
            Toast.makeText(requireContext(), "스크랩 완료!", Toast.LENGTH_SHORT).show()
        }
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
                binding.ibHomeAlert.setOnSingleClickListener {
                    noAlertBalloon.showAlignBottom(it)
                    noAlertBalloon.dismiss()
                }
            } else if (alertAmount >= 1) {
                insightCountView.text = "${alertAmount}개"
                binding.ibHomeAlert.setImageResource(R.drawable.ic_home_yes_alert)
                binding.ibHomeAlert.setOnSingleClickListener {
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

    private fun clickAddSeedBtn() {
        val intent = Intent(requireActivity(), InsightWriteActivity::class.java)
        binding.fabHomeAddInsight.setOnSingleClickListener {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION)
            App.trackEvent("${viewModel.memberId.value} + 씨앗 심기")
            startActivity(intent)
        }
    }

    private fun setThook() {
        viewModel.gatherdThook.observe(viewLifecycleOwner) { thookCount ->
            binding.tvHomeGathredThook.text = thookCount.toString()
        }
    }

    override fun onDestroyView() {
        _caveAdapter = null
        _insightAdapter = null
        super.onDestroyView()
    }
}
