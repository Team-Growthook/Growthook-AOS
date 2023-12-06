package com.growthook.aos.presentation.insight.actionplan

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivityActionplanInsightBinding
import com.growthook.aos.util.base.BaseActivity

class ActionplanInsightActivity :
    BaseActivity<ActivityActionplanInsightBinding>({ ActivityActionplanInsightBinding.inflate(it) }) {
    private var _editTextAdapter: ActionplanEdittextAdapter? = null
    private val viewModel by viewModels<ActionplanInsightViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foldInsightContent()
        initEditTextAdapter()
        observeActionplanList()
        observeButtonEnabled()
        clickPlusBtn()
    }

    private fun foldInsightContent() {
        binding.clActionInsightCollapse.setOnClickListener {
            binding.clActionInsightCollapse.visibility = View.GONE
            binding.clActionInsightExpanded.visibility = View.VISIBLE
        }
        binding.clActionInsightExpanded.setOnClickListener {
            binding.clActionInsightExpanded.visibility = View.GONE
            binding.clActionInsightCollapse.visibility = View.VISIBLE
        }
    }

    private fun initEditTextAdapter() {
        _editTextAdapter = ActionplanEdittextAdapter(
            list = viewModel.actionplanList.value ?: mutableListOf(""),
            onAddItem = { viewModel.addItem("") },
            onEditTextChanged = { position, text -> viewModel.updateItem(position, text) },
        )
        binding.rcvActionInsightEdittext.adapter = _editTextAdapter
    }

    private fun clickPlusBtn() {
        binding.ivActionInsightPlus.setOnClickListener {
            _editTextAdapter?.addItem("")
        }
    }

    private fun observeActionplanList() {
        viewModel.actionplanList.observe(this) {
            if (it.isNotEmpty()) {
                viewModel.isButtonEnabled.value = true
            }
        }
    }

    private fun observeButtonEnabled() {
        viewModel.isButtonEnabled.observe(this) { isEnabled ->
            if (isEnabled == true) {
                binding.tvActionInsightComplete.setTextColor(Color.parseColor("#23B877"))
            } else {
                binding.tvActionInsightComplete.setTextColor(Color.parseColor("#6B6E82"))
            }
        }
    }
}
