package com.growthook.aos.presentation.insight.noactionplan

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivitySeedModifyBinding
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.extension.CommonTextWatcher
import com.growthook.aos.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeedModifyActivity : BaseActivity<ActivitySeedModifyBinding>({
    ActivitySeedModifyBinding.inflate(it)
}) {

    private val viewModel by viewModels<SeedModifyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeedModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSetBeforeModifyInfo()
        initGetSeedModifyEdt()
        initSetClickGoalMonth()
        initSetBtnEnabled()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.currentFocus?.let { hideKeyboard(it) }
        with (binding) {
            edtSeedModifyInsight.clearFocus()
            edtSeedModifyMemo.clearFocus()
            edtSeedModifySource.clearFocus()
            edtSeedModifyUrl.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun initSetBeforeModifyInfo() {
        // getSeedIntentInfo()

        viewModel.seedInfo.observe(this) { seedInfo ->
            with (binding) {
                edtSeedModifyInsight.setText(seedInfo.insight)
                edtSeedModifyMemo.setText(seedInfo.memo)
                edtSeedModifySource.setText(seedInfo.source)
                edtSeedModifyUrl.setText(seedInfo.url)
                tvSeedModifyGoalSelected.text = "${seedInfo.goalMonth}개월"
                tvSeedModifyCaveSelected.text = seedInfo.cave
            }
            with (viewModel) {
                setInsightModify(seedInfo.insight)
                setMemoModify(seedInfo.memo ?: "")
                setSourceModify(seedInfo.source)
                setUrlModify(seedInfo.url ?: "")
            }
        }
    }

    private fun getSeedIntentInfo() {
        // TODO 인사이트 조회 뷰에서 넘어온 cave 정보 받아서 SeedInfo에 저장
    }

    private fun initGetSeedModifyEdt() {
        val insightModifyTextWatcher = CommonTextWatcher( afterChanged = { edtInsightModify ->
            viewModel.setInsightModify(edtInsightModify.toString())
        })
        val memoModifyTextWatcher = CommonTextWatcher( afterChanged = { edtMemoModify ->
            viewModel.setMemoModify(edtMemoModify.toString())
        })
        val sourceModifyTextWatcher = CommonTextWatcher( afterChanged = { edtSourceModify ->
            viewModel.setSourceModify(edtSourceModify.toString())
        })
        val urlModifyTextWatcher = CommonTextWatcher( afterChanged = { edtUrlModify ->
            viewModel.setUrlModify(edtUrlModify.toString())
        })

        with (binding) {
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
            with (binding.btnSeedModify) {
                if (it) {
                    isEnabled = true
                    clickSeedModifyBtn()
                } else {
                    isEnabled = false
                }
            }
        }
    }

    private fun clickSeedModifyBtn() {
        // TODO 씨앗 수정 완료 버튼 클릭 시 로직
    }
}
