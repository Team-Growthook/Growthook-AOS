package com.growthook.aos.presentation.cavecreate

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        initMakeStorageView()
        initGetStorageContent()
        initClickCloseBtn()
    }

    private fun initMakeStorageView() {
        clickOpenSwitchBtn()
        setBtnEnabled()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.currentFocus?.let { hideKeyboard(it) }
        with(binding) {
            edtStorageName.clearFocus()
            edtStorageIntroduction.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun initClickCloseBtn() {
        binding.btnStorageClose.setOnClickListener {
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
                        positiveAction = {}
                    ).show(supportFragmentManager, "delete dialog")
            } else {
                //
            }
        }
    }

    private fun initGetStorageContent() {
        val nameTextWatcher = CommonTextWatcher(afterChanged = { edtName ->
            viewModel.getStorageName(edtName.toString())
        })
        val introductionTextWatcher = CommonTextWatcher(afterChanged = { edtIntroduction ->
            viewModel.getStorageIntroduction(edtIntroduction.toString())
        })
        with(binding) {
            edtStorageName.addTextChangedListener(nameTextWatcher)
            edtStorageIntroduction.addTextChangedListener(introductionTextWatcher)
        }
    }

    private fun setBtnEnabled() {
        viewModel.checkBtnEnabled.observe(this) {
            with(binding.btnStorageCreate) {
                if (it) {
                    isEnabled = true
                    clickStorageBtn()
                } else {
                    isEnabled = false
                }
            }
        }
    }

    private fun clickStorageBtn() {
        binding.btnStorageCreate.setOnClickListener {
            postNewCave()
        }
    }

    private fun postNewCave() {
        viewModel.postNewCave(viewModel.storageName.value.toString(), viewModel.storageIntroduction.value.toString())
        viewModel.postCaveSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                sendNewStorageData()
                Toast.makeText(this, "새 동굴을 만들었어요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendNewStorageData() {
        val intent = Intent(this, SeeNewCaveActivity::class.java)
        with (binding) {
            intent.putExtra(
                NEW_STORAGE,
                NewCaveIntent(
                    edtStorageName.text.toString(),
                    edtStorageIntroduction.text.toString()
                )
            )
        }
        startActivity(intent)
        finish()
    }

    companion object {
        const val NEW_STORAGE = "NEW_STORAGE"
    }
}