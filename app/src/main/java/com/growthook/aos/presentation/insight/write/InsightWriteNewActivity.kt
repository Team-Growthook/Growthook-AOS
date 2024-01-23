package com.growthook.aos.presentation.insight.write

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityInsightWriteNewBinding
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.base.BaseAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class InsightWriteNewActivity: BaseActivity<ActivityInsightWriteNewBinding>({
    ActivityInsightWriteNewBinding.inflate(it)
}) {

    private val viewModel by viewModels<InsightWriteNewViewModel>()
    private lateinit var seedUrl: String
    private var seedId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsightWriteNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initShowDialog()
        clickBackBtn()
        initMakeNewInsightWriteView()
        clickCreateNewActionPlanBtn()
    }

    private fun initShowDialog() {
        BaseAlertDialog.Builder()
            .setCancelable(false)
            .build(
                type = BaseAlertDialog.DialogType.SINGLE_INTENDED,
                title = "새로운 성장의 씨앗을 심었어요!",
                description = "이제 발견한 가치를 행동으로 옮겨\n한 단계 성장해보세요.",
                positiveText = "확인",
                negativeText = "",
                tipText = "Tip. 액션을 완료하면\n성장의 보상 '쑥'을 얻을 수 있어요.",
                isBackgroundImageVisility = false,
                isDescriptionVisility = true,
                isRemainThookVisility = false,
                isTipVisility = true,
                negativeAction = {},
                positiveAction = {}
            ).show(supportFragmentManager, "show dialog")
    }

    private fun initMakeNewInsightWriteView() {

        seedId = intent.getIntExtra(NEW_SEED_ID , 0)
        viewModel.getNewSeedData(seedId)

        viewModel.newSeedData.observe(this) { seed ->
            with (binding) {
                tvInsightWriteNewChip.text = seed.caveName
                tvInsightWriteNewTitle.text = seed.title
                tvInsightWriteNewContent.text = seed.content
                tvInsightWriteNewDate.text = seed.date
                tvInsightWriteNewContentChipTitle.text = seed.source

                seedUrl = seed?.url.toString()

                if (seedUrl.length >= 35) {
                    "${seedUrl.take(35)}...".also { tvInsightWriteNewUrl.text = it }
                } else if (seedUrl.isNullOrEmpty()) {
                    dividerInsightWriteNewThird.visibility = View.GONE
                    tvInsightWriteNewUrl.visibility = View.GONE
                } else {
                    tvInsightWriteNewUrl.text = seedUrl
                }
                "D-${seed?.remainingDays}".also { tvInsightWriteNewDday.text = it }

                if (seed?.isScraped == true) {
                    ivInsightWriteNewSeed.setImageResource(R.drawable.ic_scrap_selected)
                } else {
                    ivInsightWriteNewSeed.setImageResource(R.drawable.ic_scrap_unselected)
                }
            }
        }
    }

    private fun clickCreateNewActionPlanBtn() {
        binding.btnInsightWriteNewActionplan.setOnClickListener {
            // TODO 액션플랜 생성 뷰로 넘어가는 로직 추가
        }
    }

    private fun clickBackBtn() {
        binding.btnInsightWriteClose.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val NEW_SEED_ID = "NEW_SEED_ID"
    }
}