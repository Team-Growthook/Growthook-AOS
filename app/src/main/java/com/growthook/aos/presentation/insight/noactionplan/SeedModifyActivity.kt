package com.growthook.aos.presentation.insight.noactionplan

import android.os.Bundle
import com.growthook.aos.databinding.ActivitySeedModifyBinding
import com.growthook.aos.util.base.BaseActivity

class SeedModifyActivity : BaseActivity<ActivitySeedModifyBinding>({
    ActivitySeedModifyBinding.inflate(it)
}) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeedModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
