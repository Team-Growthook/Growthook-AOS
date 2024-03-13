package com.growthook.aos.presentation.cavecreate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivitySeeNewCaveBinding
import com.growthook.aos.presentation.insight.noactionplan.InsightMenuBottomsheet
import com.growthook.aos.presentation.insight.write.InsightWriteActivity
import com.growthook.aos.presentation.model.NewCaveIntent
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.extension.getParcelable
import com.growthook.aos.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeNewCaveActivity : BaseActivity<ActivitySeeNewCaveBinding>({
    ActivitySeeNewCaveBinding.inflate(it)
}) {

    private val viewModel by viewModels<CreateNewCaveViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeNewCaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSetNewCaveView()
        initClickCloseBtn()
        setChbClickable()
        clickIsLockBtn()
        clickAddSeedBtn()
    }

    private fun initSetNewCaveView() {
        getStorageData()
        setNickName()
    }

    private fun getStorageData() {
        val newCaveIntentData = intent.getParcelable(NEW_CAVE_INFO, NewCaveIntent::class.java)
        with(binding) {
            tvSeeCaveTitle.text = newCaveIntentData?.name ?: ""
            tvSeeCaveSubTitle.text = newCaveIntentData?.introduction ?: ""
        }
    }

    private fun setNickName() {
        viewModel.getNickName()

        viewModel.nickName.observe(this) { nickName ->
            binding.tvSeeCaveUserNickname.text = nickName
        }
    }

    private fun initClickCloseBtn() {
        binding.btnCaveClose.setOnClickListener {
            finish()
        }
    }

    private fun setChbClickable() {
        binding.chbSeeCaveScrap.isClickable = false
    }

    private fun clickIsLockBtn() {
        binding.ivSeeCaveIsLockBtn.setOnClickListener {
            BaseAlertDialog.Builder()
                .setCancelable(false)
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
                    remainThookText = "",
                ).show(supportFragmentManager, InsightMenuBottomsheet.DELETE_DIALOG)
        }
    }

    private fun clickAddSeedBtn() {
        binding.btnSeeCaveMakeSeed.setOnSingleClickListener {
            val intent = Intent(this, InsightWriteActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val NEW_CAVE_INFO = "NEW_CAVE_INFO"

        fun getIntent(context: Context, newCaveIntent: NewCaveIntent): Intent {
            return Intent(context, SeeNewCaveActivity::class.java).apply {
                putExtra(NEW_CAVE_INFO, newCaveIntent)
            }
        }
    }
}
