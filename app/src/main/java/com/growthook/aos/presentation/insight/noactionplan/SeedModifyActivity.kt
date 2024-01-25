package com.growthook.aos.presentation.insight.noactionplan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.growthook.aos.databinding.ActivitySeedModifyBinding
import com.growthook.aos.domain.entity.SeedInfo
import com.growthook.aos.presentation.insight.noactionplan.model.SeedModifyIntent
import com.growthook.aos.presentation.insight.write.InsightWriteActivity
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.extension.CommonTextWatcher
import com.growthook.aos.util.extension.getParcelable
import com.growthook.aos.util.extension.hideKeyboard
import com.growthook.aos.util.extension.setOnSingleClickListener
import com.growthook.aos.util.selectcave.CaveSelect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeedModifyActivity : BaseActivity<ActivitySeedModifyBinding>({
    ActivitySeedModifyBinding.inflate(it)
}) {

    private val viewModel by viewModels<SeedModifyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeedModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSetBackBtn()
        initSetBeforeModifyInfo()
        initGetSeedModifyEdt()
        initSetClickGoalMonth()
        initSetBtnEnabled()
        initSetSelectCaveBottomSheet()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.currentFocus?.let { hideKeyboard(it) }
        with(binding) {
            edtSeedModifyInsight.clearFocus()
            edtSeedModifyMemo.clearFocus()
            edtSeedModifySource.clearFocus()
            edtSeedModifyUrl.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun initSetBackBtn() {
        binding.btnSeedModifyBack.setOnClickListener {
            finish()
        }
    }

    private fun initSetBeforeModifyInfo() {
        getSeedIntentInfo()

        viewModel.seedInfo.observe(this) { seedInfo ->
            with(binding) {
                edtSeedModifyInsight.setText(seedInfo.insight)
                edtSeedModifyMemo.setText(seedInfo.memo)
                edtSeedModifySource.setText(seedInfo.source)
                edtSeedModifyUrl.setText(seedInfo.url)
                tvSeedModifyGoalSelected.text = "${seedInfo.goalMonth}개월"
                tvSeedModifyCaveSelected.text = seedInfo.cave
            }
            with(viewModel) {
                setInsightModify(seedInfo.insight)
                setMemoModify(seedInfo.memo ?: "")
                setSourceModify(seedInfo.source)
                setUrlModify(seedInfo.url ?: "")
                originCave.value = seedInfo.cave
                setCaveModify(seedInfo.cave)
            }
        }
    }

    private fun getSeedIntentInfo() {
        val seedModifyIntent =
            intent.getParcelable(SEED_ID, SeedModifyIntent::class.java)
        viewModel.setSeedInfo(
            SeedInfo(
                seedModifyIntent?.seedId ?: 0,
                seedModifyIntent?.insight ?: "",
                seedModifyIntent?.memo ?: "",
                seedModifyIntent?.cave ?: "",
                seedModifyIntent?.source ?: "",
                seedModifyIntent?.url ?: "",
                seedModifyIntent?.goalMonth ?: 0,
            ),
        )
    }

    private fun initGetSeedModifyEdt() {
        val insightModifyTextWatcher = CommonTextWatcher(afterChanged = { edtInsightModify ->
            viewModel.setInsightModify(edtInsightModify.toString())
        })
        val memoModifyTextWatcher = CommonTextWatcher(afterChanged = { edtMemoModify ->
            viewModel.setMemoModify(edtMemoModify.toString())
        })
        val sourceModifyTextWatcher = CommonTextWatcher(afterChanged = { edtSourceModify ->
            viewModel.setSourceModify(edtSourceModify.toString())
        })
        val urlModifyTextWatcher = CommonTextWatcher(afterChanged = { edtUrlModify ->
            viewModel.setUrlModify(edtUrlModify.toString())
        })

        with(binding) {
            edtSeedModifyInsight.addTextChangedListener(insightModifyTextWatcher)
            edtSeedModifyMemo.addTextChangedListener(memoModifyTextWatcher)
            edtSeedModifySource.addTextChangedListener(sourceModifyTextWatcher)
            edtSeedModifyUrl.addTextChangedListener(urlModifyTextWatcher)
        }
    }

    private fun initSetClickGoalMonth() {
        binding.layoutSeedModifyGoal.setOnClickListener {
            Toast.makeText(this, "목표 기간은 수정할 수 없어요", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initSetBtnEnabled() {
        viewModel.checkSeedModifyBtnEnabled.observe(this) {
            with(binding.btnSeedModify) {
                if (it) {
                    isEnabled = true
                    clickSeedModifyBtn()
                } else {
                    isEnabled = false
                }
            }
        }
    }

    private fun initSetSelectCaveBottomSheet() {
        binding.layoutSeedModifyCave.setOnClickListener {
            binding.layoutSeedModifyCave.requestFocusFromTouch()
            CaveSelect.Builder().build(
                CaveSelect.CaveSelectType.NO_API,
                clickBtnAction = { cave ->
                    viewModel.selectedCaveId.value = cave.id
                    binding.tvSeedModifyCaveSelected.text = cave.name
                    viewModel.setCaveModify(cave.name)
                },
            ).show(supportFragmentManager, InsightWriteActivity.TAG_BOTTOM_SHEET)
        }
    }

    private fun clickSeedModifyBtn() {
        binding.btnSeedModify.setOnSingleClickListener {
            viewModel.modifySeed(
                binding.edtSeedModifyInsight.text.toString(),
                binding.edtSeedModifyMemo.text.toString(),
                binding.edtSeedModifySource.text.toString(),
                binding.edtSeedModifyUrl.text.toString(),
            )

            lifecycleScope.launch {
                viewModel.selectedCaveId.collect { caveId ->
                    if (caveId != 0) {
                        viewModel.moveSeed()
                    }
                }
            }

            sendSeedModifyInfo()
        }
    }

    private fun sendSeedModifyInfo() {
        viewModel.isModifySuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(this, "씨앗이 수정되었어요", Toast.LENGTH_SHORT).show()
                startActivity(
                    NoActionplanInsightActivity.getIntent(
                        this,
                        viewModel.seedInfo.value?.seedId ?: 0,
                    ),
                )
                finish()
            }
        }
    }

    companion object {
        private const val SEED_ID = "seedId"

        fun getIntent(context: Context, seedModifyIntent: SeedModifyIntent): Intent {
            return Intent(context, SeedModifyActivity::class.java).apply {
                putExtra(SEED_ID, seedModifyIntent)
            }
        }
    }
}
