package com.growthook.aos.presentation.insight.noactionplan

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
    private var seedId: Int = 0
    private lateinit var seedUrl: String
    private var isSeedSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSeedIdFromHome()
        observeSeedDetail()
        observeEvent()
        setClickListeners()
    }

    private fun getSeedIdFromHome() {
        seedId = intent.getIntExtra(SEED_ID, 0)
        viewModel.seedId = seedId
        Timber.d("인사이트 id $seedId")
        viewModel.getSeedDetail()
    }

    private fun observeSeedDetail() {
        viewModel.seedData.observe(this) { seed ->
            Timber.d("seed data:: $seed")
            with(binding) {
                tvNoactionInsightTitle.text = seed?.title
                tvNoactionInsightContent.text = seed?.content
                tvNoactionInsightDate.text = seed?.date
                tvNoactionInsightChip.text = seed?.caveName
                tvNoactionInsightContentChipTitle.text = seed?.source

                seedUrl = seed?.url.toString()

                if (seedUrl.length >= 35) {
                    "${seedUrl.take(35)}...".also { tvNoactionInsightUrl.text = it }
                } else if (seedUrl.isNullOrEmpty()) {
                    dividerNoactionInsightThird.visibility = View.GONE
                    tvNoactionInsightUrl.visibility = View.GONE
                } else {
                    tvNoactionInsightUrl.text = seedUrl
                }
                "D-${seed?.remainingDays}".also { tvNoactionInsightDday.text = it }

                if (seed?.isScraped == true) {
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
        clickInsightSeed()
    }

    private fun clickInsightSeed() {
        binding.ivNoactionInsightSeed.setOnClickListener {
            isSeedSelected = !isSeedSelected
            viewModel.changeSeedScrap(seedId)
            if (isSeedSelected) {
                binding.ivNoactionInsightSeed.setImageResource(R.drawable.ic_scrap_selected)
                Toast.makeText(this, "씨앗 스크랩 완료", Toast.LENGTH_SHORT).show()
            } else {
                binding.ivNoactionInsightSeed.setImageResource(R.drawable.ic_scrap_unselected)
            }
        }
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
            startActivity(AddActionplanActivity.getIntent(this, seedId))
        }
    }

    private fun observeEvent() {
        viewModel.event.observe(this) { event ->
            when (event) {
                NoActionplanInsightViewModel.Event.DeleteSeedSuccess -> {
                    finish()
                }

                else -> {}
            }
        }
    }

    companion object {
        private const val SEED_ID = "seedId"

        fun getIntent(context: Context, seedId: Int): Intent {
            return Intent(context, NoActionplanInsightActivity::class.java).apply {
                putExtra(SEED_ID, seedId)
            }
        }
    }
}
