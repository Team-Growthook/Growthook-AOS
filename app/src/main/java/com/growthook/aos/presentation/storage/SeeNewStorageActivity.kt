package com.growthook.aos.presentation.storage

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.growthook.aos.data.entity.NewStorage
import com.growthook.aos.databinding.ActivitySeeNewStorageBinding
import com.growthook.aos.presentation.storage.CreateNewStorageActivity.Companion.NEW_STORAGE
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.extension.getParcelable
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

        getStorageData()
    }


    private fun getStorageData() {
        val newStorageData = intent.getParcelable(NEW_STORAGE, NewStorage::class.java)
        with (binding) {
            tvSeeStorageTitle.text = newStorageData?.name ?: ""
            tvSeeStorageSubTitle.text = newStorageData?.introduction ?: ""
        }
    }
}