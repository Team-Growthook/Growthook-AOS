package com.growthook.aos.presentation.insight.noactionplan

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivitySeedModifyBinding
import com.growthook.aos.util.base.BaseActivity

class SeedModifyActivity : BaseActivity<ActivitySeedModifyBinding>({
    ActivitySeedModifyBinding.inflate(it)
}) {

    private val viewModel by viewModels<SeedModifyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeedModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSetClickGoalMonth()
    }

    private fun initSetClickGoalMonth() {
        binding.layoutSeedModifyGoal.setOnClickListener {
            Toast.makeText(this, "목표 기간은 수정할 수 없어요", Toast.LENGTH_SHORT).show()
        }
    }
}
