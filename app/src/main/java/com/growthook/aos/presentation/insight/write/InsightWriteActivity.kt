package com.growthook.aos.presentation.insight.write

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import com.growthook.aos.App
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityInsightWriteBinding
import com.growthook.aos.presentation.insight.write.InsightWriteNewActivity.Companion.NEW_SEED_ID
import com.growthook.aos.presentation.main.MainComposeActivity
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.extension.CommonTextWatcher
import com.growthook.aos.util.extension.hideKeyboard
import com.growthook.aos.util.extension.setOnSingleClickListener
import com.growthook.aos.util.selectcave.CaveSelect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightWriteActivity : BaseActivity<ActivityInsightWriteBinding>({
    ActivityInsightWriteBinding.inflate(it)
}) {

    private val viewModel by viewModels<InsightWriteViewModel>()
    private lateinit var goalSelectBottomSheet: InsightWriteGoalSelectBottomSheetFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsightWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initGetInsightWriteEdt()
        initSetSelectCaveBottomSheet()
        initSetSelectGoalBottomSheet()
        initSetBtnEnabled()
        clickBackBtn()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.currentFocus?.let { hideKeyboard(it) }
        with(binding) {
            edtInsightWriteInsight.clearFocus()
            edtInsightWriteMemo.clearFocus()
            edtInsightWriteSource.clearFocus()
            edtInsightWriteUrl.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun initGetInsightWriteEdt() {
        val insightTextWatcher = CommonTextWatcher(afterChanged = { edtInsight ->
            viewModel.setInsight(edtInsight.toString())
        })
        val memoTextWatcher = CommonTextWatcher(afterChanged = { edtMemo ->
            viewModel.setMemo(edtMemo.toString())
        })
        val sourceTextWatcher = CommonTextWatcher(afterChanged = { edtSource ->
            viewModel.setSource(edtSource.toString())
        })
        val urlTextWatcher = CommonTextWatcher(afterChanged = { edtUrl ->
            viewModel.setUrl(edtUrl.toString())

            if (!viewModel.isUrlValid(edtUrl.toString())) {
                binding.tvInsightWriteUrlError.visibility = View.VISIBLE
                binding.layoutUrlError.visibility = View.VISIBLE
                binding.edtInsightWriteUrl.setTextColor(getColor(R.color.Red200))
            } else {
                binding.tvInsightWriteUrlError.visibility = View.INVISIBLE
                binding.layoutUrlError.visibility = View.INVISIBLE
                binding.edtInsightWriteUrl.setTextColor(getColor(R.color.White000))
            }
        })

        with(binding) {
            edtInsightWriteInsight.addTextChangedListener(insightTextWatcher)
            edtInsightWriteMemo.addTextChangedListener(memoTextWatcher)
            edtInsightWriteSource.addTextChangedListener(sourceTextWatcher)
            edtInsightWriteUrl.addTextChangedListener(urlTextWatcher)
        }
    }

    private fun initSetSelectCaveBottomSheet() {
        binding.layoutInsightWriteCave.setOnSingleClickListener {
            binding.layoutInsightWriteCave.requestFocusFromTouch()
            CaveSelect.Builder().build(
                CaveSelect.CaveSelectType.NO_API,
                clickBtnAction = {
                    viewModel.selectedCaveId.value = it.id
                    setSelectedCaveEditText(it.name)
                },
            ).show(supportFragmentManager, TAG_BOTTOM_SHEET)
        }
    }

    private fun setSelectedCaveEditText(caveName: String) {
        with(binding) {
            tvInsightWriteCaveHint.visibility = View.GONE
            tvInsightWriteCaveSelected.visibility = View.VISIBLE
            tvInsightWriteCaveSelected.text = caveName
        }
    }

    private fun initSetSelectGoalBottomSheet() {
        setGoalMonth(1)

        goalSelectBottomSheet = InsightWriteGoalSelectBottomSheetFragment()

        binding.layoutInsightWriteGoal.setOnSingleClickListener {
            goalSelectBottomSheet.setOnGoalSelectedListener(object :
                InsightWriteGoalSelectBottomSheetFragment.OnGoalSelectedListener {
                override fun onGoalSelected(goalMonth: Int) {
                    setGoalMonth(goalMonth)
                }
            })
            goalSelectBottomSheet.show(supportFragmentManager, TAG_BOTTOM_SHEET)
        }
    }

    private fun setSelectedGoalEditText(goalMonth: Int) {
        with(binding) {
            tvInsightWriteGoalHint.visibility = View.GONE
            tvInsightWriteGoalSelected.visibility = View.VISIBLE
            tvInsightWriteGoalSelected.text = goalMonth.toString() + DISPLAY_GOAL_MONTH
        }
    }

    private fun setGoalMonth(goalMonth: Int) {
        setSelectedGoalEditText(goalMonth)
        viewModel.setSelectedGoalMonth(goalMonth)
    }

    private fun initSetBtnEnabled() {
        viewModel.checkInsightWriteBtnEnabled.observe(this) {
            with(binding.btnInsightWrite) {
                if (it) {
                    isEnabled = true
                    clickInsightWriteBtn()
                } else {
                    isEnabled = false
                }
            }
        }
    }

    private fun clickInsightWriteBtn() {
        binding.btnInsightWrite.setOnSingleClickListener {
            viewModel.postNewSeed()
            viewModel.postSeedSuccess.observe(this) { isSuccess ->
                if (isSuccess) {
                    sendNewInsightInfo()
                }
            }
        }
    }

    private fun sendNewInsightInfo() {
        val intent = Intent(this, InsightWriteNewActivity::class.java)
        viewModel.seedId.observe(this) {
            intent.putExtra(NEW_SEED_ID, it)
            App.trackEvent("${MainComposeActivity.USER_ID} + 등록하기")
            startActivity(intent)
            finish()
        }
    }

    private fun clickBackBtn() {
        binding.btnInsightWriteBack.setOnClickListener {
            App.trackEvent("${MainComposeActivity.USER_ID} + 메인으로 나가기")
            finish()
        }
    }

    companion object {
        const val TAG_BOTTOM_SHEET = "SHOW"
        const val DISPLAY_GOAL_MONTH = "개월"
    }
}
