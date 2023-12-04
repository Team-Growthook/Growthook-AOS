package com.growthook.aos.presentation.insight.actionplan

import android.os.Bundle
import android.view.View
import com.growthook.aos.databinding.ActivityActionplanInsightBinding
import com.growthook.aos.util.base.BaseActivity

class ActionplanInsightActivity :
    BaseActivity<ActivityActionplanInsightBinding>({ ActivityActionplanInsightBinding.inflate(it) }) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foldInsightContent()
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
}
