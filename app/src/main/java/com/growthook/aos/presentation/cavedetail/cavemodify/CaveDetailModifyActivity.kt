package com.growthook.aos.presentation.cavedetail.cavemodify

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivityCaveDetailModifyBinding
import com.growthook.aos.presentation.MainActivity
import com.growthook.aos.presentation.cavedetail.CaveDetailActivity
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaveDetailModifyActivity :
    BaseActivity<ActivityCaveDetailModifyBinding>({ ActivityCaveDetailModifyBinding.inflate(it) }) {
    private val viewModel by viewModels<CaveDetailModifyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.edtCaveModifyName.setText(intent.getStringExtra("caveName"))
        observeIsModified()
        canClickFinishBtn()
        clickBackNavi()
        clickFinishBtn()
    }

    private fun observeIsModified() {
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setIsModified(true)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }

        binding.edtCaveModifyName.addTextChangedListener(textWatcher)
        binding.edtCaveModifyDesc.addTextChangedListener(textWatcher)
    }

    private fun canClickFinishBtn() {
        viewModel.isCaveModified.observe(this) { isModified ->
            binding.btnCaveModify.isEnabled = isModified
        }
    }

    private fun clickBackNavi() {
        binding.tbCaveModify.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun clickFinishBtn() {
        binding.btnCaveModify.setOnClickListener {
            val intent = Intent(this, CaveDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.currentFocus?.let { hideKeyboard(it) }
        with(binding) {
            edtCaveModifyName.clearFocus()
            edtCaveModifyDesc.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }
}
