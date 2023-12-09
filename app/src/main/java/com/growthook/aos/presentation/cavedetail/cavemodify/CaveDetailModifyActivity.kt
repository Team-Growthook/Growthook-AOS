package com.growthook.aos.presentation.cavedetail.cavemodify

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivityCaveDetailModifyBinding
import com.growthook.aos.presentation.cavedetail.CaveDetailActivity
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.extension.CommonTextWatcher
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
        val textWatcher = CommonTextWatcher(onChanged = { _, _, _, _ ->
            viewModel.setIsModified(true)
        })

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
            val intent = Intent(this, CaveDetailActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun clickFinishBtn() {
        binding.btnCaveModify.setOnClickListener {
            val intent = Intent(this, CaveDetailActivity::class.java)
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
