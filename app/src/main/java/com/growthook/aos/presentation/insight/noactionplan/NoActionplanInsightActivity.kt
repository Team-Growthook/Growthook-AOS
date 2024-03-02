package com.growthook.aos.presentation.insight.noactionplan

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
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
                tvNoactionInsightMemo.text = seed.content
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
        copyUrl()
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

    private fun copyUrl() {
        binding.clNoactionInsightContentChip.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("seedUrl", seedUrl)
            clipboard.setPrimaryClip(clip)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                Toast.makeText(this, "링크를 복사했어요", Toast.LENGTH_SHORT).show()
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
