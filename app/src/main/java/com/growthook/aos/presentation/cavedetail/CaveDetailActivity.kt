package com.growthook.aos.presentation.cavedetail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.growthook.aos.databinding.ActivityCaveDetailBinding
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.presentation.MainActivity
import com.growthook.aos.presentation.home.HomeInsightAdapter
import com.growthook.aos.presentation.insight.noactionplan.see.InsightMenuBottomsheet
import com.growthook.aos.presentation.insight.noactionplan.see.NoActionplanInsightActivity
import com.growthook.aos.util.EmptyDataObserver
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.base.BaseAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CaveDetailActivity : BaseActivity<ActivityCaveDetailBinding>({
    ActivityCaveDetailBinding.inflate(it)
}) {

    private val viewModel: CaveDetailViewModel by viewModels()

    private var _insightAdapter: HomeInsightAdapter? = null
    private lateinit var selectMenuBottomSheet: CaveDetailSelectMenuBottomSheet
    private val insightAdapter
        get() = requireNotNull(_insightAdapter) { "adapter is null" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaveDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickLock()
        selectMenuBottomSheet = CaveDetailSelectMenuBottomSheet()

        val caveId = intent.getIntExtra("caveId", 0)
        viewModel.getInsights(caveId)
        setInsightAdapter()
        setNickName()
        clickScrap(caveId)

        clickBackNavi()
        clickMainMenu()
    }


    private fun setInsightAdapter() {
        _insightAdapter = HomeInsightAdapter(::selectedItem, ::clickedScrap)
        viewModel.insights.observe(this) {
            insightAdapter.submitList(it)
            binding.tvCaveDetailInsightTitle.text = "${it.size}개의 씨앗을 모았어요!"
        }
        binding.rcvCaveDetailInsight.adapter = insightAdapter

        val longTracker = SelectionTracker.Builder<Long>(
            "caveDetailSelection",
            binding.rcvCaveDetailInsight,
            StableIdKeyProvider(binding.rcvCaveDetailInsight),
            HomeInsightAdapter.InsightDetailsLookup(binding.rcvCaveDetailInsight),
            StorageStrategy.createLongStorage(),
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything(),
        ).build()

        insightAdapter.setSelectionLongTracker(longTracker)
        insightAdapter.registerAdapterDataObserver(
            EmptyDataObserver(
                binding.rcvCaveDetailInsight,
                binding.tvCaveDetailEmptyInsight,
                binding.ivCaveDetailEmptyInsight,
            ),
        )

        longTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()

                val selectedInsight = insightAdapter.getSelectedLongInsight()

                selectedInsight?.let {
                    selectMenuBottomSheet.show(supportFragmentManager, "show")
                    Timber.d("long click 선택된 insight $it")
                    viewModel.longClickInsight.value = it
                }
            }
        })

        viewModel.isMenuDismissed.observe(this) {
            longTracker.clearSelection()
        }
    }

    private fun clickLock() {
        binding.ivCaveDetailIsLock.setOnClickListener {
            BaseAlertDialog.Builder()
                .setCancelable(true)
                .build(
                    type = BaseAlertDialog.DialogType.SINGLE_INTENDED,
                    title = "내 동굴에 친구를 초대해\n" +
                        "인사이트를 공유해요!",
                    description = "해당 기능은 추후 업데이트 예정이에요 :)",
                    positiveText = "확인",
                    negativeText = "",
                    tipText = "",
                    isBackgroundImageVisility = false,
                    isDescriptionVisility = true,
                    isRemainThookVisility = false,
                    isTipVisility = false,
                    negativeAction = {
                    },
                    positiveAction = {
                    },
                ).show(supportFragmentManager, InsightMenuBottomsheet.DELETE_DIALOG)
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
                        Toast.makeText(this, "잠금이 영구적으로 해제되었어요!", Toast.LENGTH_SHORT).show()
                        val intent =
                            Intent(this, NoActionplanInsightActivity::class.java)
                        intent.putExtra("insightId", item.insightId)
                        startActivity(intent)
                    },
                ).show(supportFragmentManager, InsightMenuBottomsheet.DELETE_DIALOG)
        } else if (!item.isAction) {
            val intent =
                Intent(this, NoActionplanInsightActivity::class.java)
            intent.putExtra("insightId", item.insightId)
            startActivity(intent)
        }
    }

    private fun clickedScrap(isScrap: Boolean) {
        viewModel.changeScrap(isScrap)
        Timber.d("스크랩 $isScrap")
        Toast.makeText(this, "스크랩 완료", Toast.LENGTH_SHORT).show()
    }

    private fun setNickName() {
        viewModel.nickname.observe(this) {
            binding.tvCaveDetailNickname.text = "$it"
        }
    }

    private fun clickScrap(caveId: Int) {
        binding.chbCaveDetailScrap.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                viewModel.getScrapedInsight()
            } else {
                viewModel.getInsights(caveId)
            }
        }
    }

    private fun clickBackNavi() {
        binding.tbCaveDetail.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun clickMainMenu() {
        binding.ibCaveDetailMainmenu.setOnClickListener {
            val bottomSheet = CaveModifySelectBottomSheet()
            bottomSheet.show(supportFragmentManager, "show")
        }
    }

    override fun onDestroy() {
        _insightAdapter = null
        super.onDestroy()
    }
}
