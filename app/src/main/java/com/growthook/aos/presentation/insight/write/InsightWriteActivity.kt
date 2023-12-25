package com.growthook.aos.presentation.insight.write

import android.os.Bundle
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivityInsightWriteBinding
import com.growthook.aos.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightWriteActivity: BaseActivity<ActivityInsightWriteBinding>({
    ActivityInsightWriteBinding.inflate(it)
}) {

    private val viewModel by viewModels<InsightWriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsightWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}