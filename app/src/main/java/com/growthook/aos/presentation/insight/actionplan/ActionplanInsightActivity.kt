package com.growthook.aos.presentation.insight.actionplan

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
        val editTextList = mutableListOf("")
        _editTextAdapter = ActionplanEdittextAdapter(editTextList)
        binding.rcvActionInsightEdittext.adapter = _editTextAdapter
        clickPlusBtn()
    }

    private fun clickPlusBtn() {
        binding.ivActionInsightPlus.setOnClickListener {
            _editTextAdapter?.addItem("")
        }
    }
}
