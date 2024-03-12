package com.growthook.aos.presentation.insight.write

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.activity.viewModels
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityInsightWriteNewBinding
import com.growthook.aos.presentation.insight.noactionplan.add.AddActionplanActivity
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightWriteNewActivity : BaseActivity<ActivityInsightWriteNewBinding>({
    ActivityInsightWriteNewBinding.inflate(it)
}) {

    private val viewModel by viewModels<InsightWriteNewViewModel>()
    private lateinit var seedSource: String
    private lateinit var seedUrl: String
    private var seedId: Int = 0
    private var isSeedSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsightWriteNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initShowDialog()
        clickBackBtn()
        initMakeNewInsightWriteView()
        clickCreateNewActionPlanBtn()
        clickSeedScrap()
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
                positiveAction = {},
                remainThookText = "",
            ).show(supportFragmentManager, "show dialog")
    }

    private fun initMakeNewInsightWriteView() {
        seedId = intent.getIntExtra(NEW_SEED_ID, 0)
        viewModel.getNewSeedData(seedId)

        viewModel.newSeedData.observe(this) { seed ->
            with(binding) {
                tvInsightWriteNewChip.text = seed.caveName
                tvInsightWriteNewTitle.text = seed.title
                tvInsightWriteNewContent.text = seed.content
                tvInsightWriteNewDate.text = seed.date

                seedSource = seed?.source.toString()
                seedUrl = seed?.url.toString()


                if (seedSource == "null" && seedUrl.isNullOrEmpty()) {
                    clInsightWriteNewContentChip.visibility = View.GONE
                } else {
                    if (seedSource == "null") {
                        dividerInsightWriteNewThird.visibility = View.GONE
                        tvInsightWriteNewContentChipTitle.visibility = View.GONE
                    } else {
                        tvInsightWriteNewContentChipTitle.text = seedSource
                    }

                    if (seedUrl.length >= 35) {
                        "${seedUrl.take(35)}...".also { tvInsightWriteNewUrl.text = it }
                    } else if (seedUrl.isNullOrEmpty()) {
                        dividerInsightWriteNewThird.visibility = View.GONE
                        tvInsightWriteNewUrl.visibility = View.GONE
                    } else {
                        tvInsightWriteNewUrl.text = seedUrl
                    }
                }

                "D-${seed?.remainingDays}".also { tvInsightWriteNewDday.text = it }

                if (seed?.isScraped == true) {
                    ivInsightWriteNewSeed.setImageResource(R.drawable.ic_scrap_selected)
                } else {
                    ivInsightWriteNewSeed.setImageResource(R.drawable.ic_scrap_unselected)
                }
            }

            moveURL()
        }
    }

    private fun moveURL() {
        binding.tvInsightWriteNewUrl.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(URLUtil.guessUrl(seedUrl))
                )
            )
        }
    }

    private fun clickSeedScrap() {
        binding.ivInsightWriteNewSeed.setOnClickListener {
            isSeedSelected = !isSeedSelected
            viewModel.changeSeedScrap(seedId)
            if (isSeedSelected) {
                binding.ivInsightWriteNewSeed.setImageResource(R.drawable.ic_scrap_selected)
                Toast.makeText(this, "씨앗 스크랩 완료", Toast.LENGTH_SHORT).show()
            } else {
                binding.ivInsightWriteNewSeed.setImageResource(R.drawable.ic_scrap_unselected)
            }
        }
    }

    private fun clickCreateNewActionPlanBtn() {
        binding.btnInsightWriteNewActionplan.setOnSingleClickListener {
            startActivity(AddActionplanActivity.getIntent(this, seedId))
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
