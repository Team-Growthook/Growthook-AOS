package com.growthook.aos.presentation.cavecreate

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivityCreateNewCaveBinding
import com.growthook.aos.presentation.model.NewCaveIntent
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.extension.CommonTextWatcher
import com.growthook.aos.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNewCaveActivity : BaseActivity<ActivityCreateNewCaveBinding>({
    ActivityCreateNewCaveBinding.inflate(it)
}) {

    private val viewModel by viewModels<CreateNewCaveViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewCaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMakeCaveView()
        initGetCaveContent()
        initClickCloseBtn()
    }

    private fun initMakeCaveView() {
        clickOpenSwitchBtn()
        setBtnEnabled()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.currentFocus?.let { hideKeyboard(it) }
        with(binding) {
            edtCaveName.clearFocus()
            edtCaveIntroduction.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun initClickCloseBtn() {
        binding.btnCaveClose.setOnClickListener {
            finish()
        }
    }

    private fun clickOpenSwitchBtn() {
        binding.switchOpen.setOnCheckedChangeListener { CompoundButton, onSwitch ->
            if (onSwitch) {
                BaseAlertDialog.Builder()
                    .setCancelable(false)
                    .build(
                        type = BaseAlertDialog.DialogType.SINGLE_INTENDED,
                        title = "내 동굴에 친구를 초대해\n  인사이트를 공유해요!",
                        description = "해당 기능은 추후 업데이트 예정이에요 :)",
                        isTipVisility = false,
                        isRemainThookVisility = false,
                        isBackgroundImageVisility = false,
                        isDescriptionVisility = true,
                        positiveText = "확인",
                        negativeText = "",
                        tipText = "",
                        negativeAction = {},
                        positiveAction = {},
                    ).show(supportFragmentManager, "delete dialog")
                binding.switchOpen.isChecked = false
            } else {
                //
            }
        }
    }

    private fun initGetCaveContent() {
        val nameTextWatcher = CommonTextWatcher(afterChanged = { edtName ->
            viewModel.getCaveName(edtName.toString())
        })
        val introductionTextWatcher = CommonTextWatcher(afterChanged = { edtIntroduction ->
            viewModel.getCaveIntroduction(edtIntroduction.toString())
        })
        with(binding) {
            edtCaveName.addTextChangedListener(nameTextWatcher)
            edtCaveIntroduction.addTextChangedListener(introductionTextWatcher)
        }
    }

    private fun setBtnEnabled() {
        viewModel.checkBtnEnabled.observe(this) {
            with(binding.btnCaveCreate) {
                if (it) {
                    isEnabled = true
                    clickCreateCaveBtn()
                } else {
                    isEnabled = false
                }
            }
        }
    }

    private fun clickCreateCaveBtn() {
        binding.btnCaveCreate.setOnClickListener {
            postNewCave()
        }
    }

    private fun postNewCave() {
        viewModel.postNewCave()
        viewModel.postCaveSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                sendNewCaveData()
                Toast.makeText(this, "새 동굴을 만들었어요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendNewCaveData() {
        val newCaveIntent = NewCaveIntent(
            binding.edtCaveName.text.toString(),
            binding.edtCaveIntroduction.text.toString(),
        )

        startActivity(SeeNewCaveActivity.getIntent(this, newCaveIntent))
        finish()
    }
}
