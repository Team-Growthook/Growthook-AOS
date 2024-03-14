package com.growthook.aos.presentation.insight.noactionplan

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.activity.viewModels
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityNoActionplanInsightBinding
import com.growthook.aos.presentation.insight.noactionplan.add.AddActionplanActivity
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NoActionplanInsightActivity :
    BaseActivity<ActivityNoActionplanInsightBinding>({ ActivityNoActionplanInsightBinding.inflate(it) }) {
    private val viewModel by viewModels<NoActionplanInsightViewModel>()
    private val bottomSheetDialog = InsightMenuBottomsheet()
    private var seedId: Int = 0
    private lateinit var seedSource: String
    private lateinit var seedUrl: String
    private var isSeedScraped = false

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
        viewModel.getSeedDetail(seedId)
    }

    private fun observeSeedDetail() {
        viewModel.seedData.observe(this) { seed ->
            Timber.d("seed data:: $seed")
            with(binding) {
                tvNoactionInsightTitle.text = seed?.title
                tvNoactionInsightDate.text = seed?.date
                tvNoactionInsightChip.text = seed?.caveName
                tvNoactionInsightContentChipTitle.text = seed?.source

                seedSource = seed?.source.toString()
                seedUrl = seed?.url.toString()

                if (seed.content.isNullOrEmpty()) {
                    tvNoactionInsightMemo.visibility = View.GONE
                } else {
                    tvNoactionInsightMemo.text = seed.content
                }

                if (seedSource == "null" && seedUrl.isNullOrEmpty()) {
                    clNoactionInsightContentChip.visibility = View.GONE
                } else {
                    if (seedSource == "null") {
                        dividerNoactionInsightThird.visibility = View.GONE
                        tvNoactionInsightContentChipTitle.visibility = View.GONE
                    } else {
                        tvNoactionInsightContentChipTitle.text = seedSource
                    }

                    if (seedUrl.length >= 35) {
                        "${seedUrl.take(35)}...".also { tvNoactionInsightUrl.text = it }
                    } else if (seedUrl.isNullOrEmpty()) {
                        dividerNoactionInsightThird.visibility = View.GONE
                        tvNoactionInsightUrl.visibility = View.GONE
                    } else {
                        tvNoactionInsightUrl.text = seedUrl
                    }
                }

                if (seed.remainingDays < 0) {
                    dividerNoactionInsightFirst.visibility = View.GONE
                } else {
                    "D-${seed.remainingDays}".also { tvNoactionInsightDday.text = it }
                }

                if (seed?.isScraped == true) {
                    isSeedScraped = true
                    ivNoactionInsightSeed.setImageResource(R.drawable.ic_scrap_selected)
                } else {
                    isSeedScraped = false
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
        moveUrl()
    }

    private fun clickInsightSeed() {
        binding.ivNoactionInsightSeed.setOnClickListener {
            isSeedScraped = !isSeedScraped
            viewModel.changeSeedScrap(seedId)
            if (isSeedScraped) {
                binding.ivNoactionInsightSeed.setImageResource(R.drawable.ic_scrap_selected)
                Toast.makeText(this, "씨앗이 스크랩 되었어요", Toast.LENGTH_SHORT).show()
            } else {
                binding.ivNoactionInsightSeed.setImageResource(R.drawable.ic_scrap_unselected)
            }
        }
    }

    private fun clickMenu() {
        binding.ivNoactionInsightToolbarMenu.setOnSingleClickListener {
            bottomSheetDialog.show(supportFragmentManager, "show")
        }
    }

    private fun clickBackBtn() {
        binding.ivNoactionInsightBack.setOnClickListener {
            finish()
        }
    }

    private fun clickAddAction() {
        binding.btnNoactionInsight.setOnSingleClickListener {
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

    private fun moveUrl() {
        binding.tvNoactionInsightUrl.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(URLUtil.guessUrl(seedUrl)),
                ),
            )
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
