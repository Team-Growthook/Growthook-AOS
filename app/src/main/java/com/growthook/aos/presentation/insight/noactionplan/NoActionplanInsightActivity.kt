package com.growthook.aos.presentation.insight.noactionplan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityNoActionplanInsightBinding
import com.growthook.aos.presentation.insight.noactionplan.add.AddActionplanActivity
import com.growthook.aos.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NoActionplanInsightActivity :
    BaseActivity<ActivityNoActionplanInsightBinding>({ ActivityNoActionplanInsightBinding.inflate(it) }) {
    private val viewModel by viewModels<NoActionplanInsightViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeSeedDetail()
        setClickListeners()
        val insightId = intent.getIntExtra("insightId", 0)
        Timber.d("인사이트 id $insightId")
    }

    private fun observeSeedDetail() {
        viewModel.seedData.observe(this) { seed ->
            with(binding) {
                tvNoactionInsightTitle.text = seed?.title
                tvNoactionInsightContent.text = seed?.content
                tvNoactionInsightDate.text = seed?.date
                tvNoactionInsightChip.text = seed?.caveName
                tvNoactionInsightContentChipTitle.text = seed?.source
                tvNoactionInsightUrl.text = seed?.url
                "D-${seed?.remainingDays}".also { tvNoactionInsightDday.text = it }

                if (seed.isScraped) {
                    ivNoactionInsightSeed.setImageResource(R.drawable.ic_scrap_selected)
                } else {
                    ivNoactionInsightSeed.setImageResource(R.drawable.ic_scrap_unselected)
                }
            }
        }
    }

    private fun setClickListeners() {
        clickMenu()
        clickAddAction()
        clickBackBtn()
    }

    private fun clickMenu() {
        binding.ivNoactionInsightToolbarMenu.setOnClickListener {
            val bottomSheetDialog = InsightMenuBottomsheet()
            bottomSheetDialog.show(supportFragmentManager, "show")
        }
    }

    private fun clickBackBtn() {
        binding.ivNoactionInsightBack.setOnClickListener {
            finish()
        }
    }

    private fun clickAddAction() {
        binding.btnNoactionInsight.setOnClickListener {
            startActivity(AddActionplanActivity.getIntent(this, DUMMY_SEED))
        }
    }

    companion object {
        private const val SEED_ID = "seedId"
        private const val DUMMY_SEED = 113

        fun getIntent(context: Context, seedId: Int): Intent {
            return Intent(context, NoActionplanInsightActivity::class.java).apply {
                putExtra(SEED_ID, seedId)
            }
        }
    }
}
