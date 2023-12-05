package com.growthook.aos.presentation.cavedetail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.growthook.aos.databinding.ActivityCaveDetailBinding
import com.growthook.aos.presentation.insight.noactionplan.InsightMenuBottomsheet
import com.growthook.aos.util.base.BaseAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaveDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCaveDetailBinding

    private val viewModel: CaveDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaveDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickLock()

        val caveId = intent.getIntExtra("caveId", 0)
        viewModel.getCaveDetail(caveId)

    }

    private fun clickLock() {
        binding.ivCaveDetailIsLock.setOnClickListener {
            BaseAlertDialog.Builder()
                .setCancelable(true)
                .build(
                    type = BaseAlertDialog.DialogType.SINGLE_INTENDED,
                    title = "내 동굴에 친구를 초대해\n" +
                        "인사이트를 공유해요!",
                    description = "해당 기능은 추후 업데이트 예정이에요 :)",
                    positiveText = "확인",
                    negativeText = "",
                    tipText = "",
                    isBackgroundImageVisility = false,
                    isDescriptionVisility = true,
                    isRemainThookVisility = false,
                    isTipVisility = false,
                    negativeAction = {
                    },
                    positiveAction = {
                    },
                ).show(supportFragmentManager, InsightMenuBottomsheet.DELETE_DIALOG)
        }
    }
}
