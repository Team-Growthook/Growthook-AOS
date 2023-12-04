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
        binding.switchOpen.setOnCheckedChangeListener{ CompoundButton, onSwitch ->
            if (onSwitch) {
                //
            } else {
                //
            }
        }
    }

    private fun initGetStorageContent() {
        with (binding) {
            edtStorageName.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    viewModel.getStorageName(s.toString())
                }
            })

            edtStorageIntroduction.addTextChangedListener(object : TextWatcher{
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
            if (it) {
                with (binding) {
                    btnStorageCreate.isEnabled = true
                    btnStorageCreate.setBackgroundResource(R.drawable.shape_storage_btn_active)
                }
            } else {
                with (binding) {
                    btnStorageCreate.isEnabled = false
                    btnStorageCreate.setBackgroundResource(R.drawable.shape_storage_btn_inactive)
                }
            }
        }
    }
}