package com.growthook.aos.presentation.insight.write

import android.os.Bundle
import com.growthook.aos.databinding.ActivityInsightWriteNewBinding
import com.growthook.aos.util.base.BaseActivity

class InsightWriteNewActivity: BaseActivity<ActivityInsightWriteNewBinding>({
    ActivityInsightWriteNewBinding.inflate(it)
}) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsightWriteNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickBackBtn()
    }

    private fun initMakeNewInsightWriteView() {
        // TODO 씨앗 정보 뷰 서버통신 후 뷰 그리는 로직 추가
    }

    private fun clickBackBtn() {
        binding.btnInsightWriteClose.setOnClickListener {
            finish()
        }
    }
}