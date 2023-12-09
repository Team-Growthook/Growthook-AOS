package com.growthook.aos.presentation.storage

import android.os.Bundle
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivitySeeNewStorageBinding
import com.growthook.aos.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeNewStorageActivity: BaseActivity<ActivitySeeNewStorageBinding>({
    ActivitySeeNewStorageBinding.inflate(it)
}) {

    private val viewModel by viewModels<SeeNewStorageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeNewStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}