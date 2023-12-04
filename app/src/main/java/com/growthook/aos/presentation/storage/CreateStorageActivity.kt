package com.growthook.aos.presentation.storage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityCreateStorageBinding
import com.growthook.aos.util.base.BaseAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateStorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateStorageBinding
    private val viewModel by viewModels<CreateStorageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMakeStorageView()
        initGetStorageContent()
    }

    private fun initMakeStorageView() {
        clickOpenSwitchBtn()
        setBtnEnabled()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard() {
        val currentFocus = currentFocus
        if (currentFocus is EditText) {
            currentFocus.clearFocus()
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0)
        }
    }

    private fun clickOpenSwitchBtn() {
        binding.switchOpen.setOnCheckedChangeListener { _, onSwitch ->
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
            }
        }
    }

    private fun initGetStorageContent() {
        with(binding) {
            edtStorageName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    viewModel.getStorageName(s.toString())
                }
            })

            edtStorageIntroduction.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    viewModel.getStorageIntroduction(s.toString())
                }
            })
        }
    }

    private fun setBtnEnabled() {
        viewModel.checkBtnEnabled.observe(this) {
            with(binding.btnStorageCreate) {
                if (it) {
                    isEnabled = true
                    setBackgroundResource(R.drawable.shape_storage_btn_active)
                } else {
                    isEnabled = false
                    setBackgroundResource(R.drawable.shape_storage_btn_inactive)
                }
            }
        }
    }
}