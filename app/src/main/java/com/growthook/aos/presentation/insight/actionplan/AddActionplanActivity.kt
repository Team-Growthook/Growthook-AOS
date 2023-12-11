package com.growthook.aos.presentation.insight.actionplan

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivityAddActionplanBinding
import com.growthook.aos.util.base.BaseActivity

class AddActionplanActivity :
    BaseActivity<ActivityAddActionplanBinding>({ ActivityAddActionplanBinding.inflate(it) }) {
    private var _addActionplanAdapter: AddActionplanAdapter? = null
    private val viewModel by viewModels<AddActionplanViewModel>()

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
        _addActionplanAdapter = AddActionplanAdapter(
            list = viewModel.actionplanList.value ?: mutableListOf(""),
            onAddItem = { viewModel.addItem("") },
            onEditTextChanged = { position, text -> viewModel.updateItem(position, text) },
        )
        binding.rcvActionInsightEdittext.adapter = _addActionplanAdapter
    }

    private fun clickPlusBtn() {
        binding.ivActionInsightPlus.setOnClickListener {
            _addActionplanAdapter?.addItem("")
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
