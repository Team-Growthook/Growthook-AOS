package com.growthook.aos.presentation.insight.write

import android.os.Bundle
import com.growthook.aos.databinding.ActivityInsightWriteNewBinding
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.base.BaseAlertDialog

class InsightWriteNewActivity: BaseActivity<ActivityInsightWriteNewBinding>({
    ActivityInsightWriteNewBinding.inflate(it)
}) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsightWriteNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initShowDialog()
        clickBackBtn()
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
        // TODO 씨앗 정보 뷰 서버통신 후 뷰 그리는 로직 추가
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
}