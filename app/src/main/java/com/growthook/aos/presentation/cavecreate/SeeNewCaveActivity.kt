package com.growthook.aos.presentation.cavecreate

import android.os.Bundle
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivitySeeNewCaveBinding
import com.growthook.aos.presentation.cavecreate.CreateNewCaveActivity.Companion.NEW_STORAGE
import com.growthook.aos.presentation.model.NewCaveIntent
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.extension.getParcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeNewCaveActivity: BaseActivity<ActivitySeeNewCaveBinding>({
    ActivitySeeNewCaveBinding.inflate(it)
}) {

    // private val viewModel by viewModels<SeeNewCaveViewModel>()
    private val viewModel by viewModels<CreateNewCaveViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeNewCaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSetNewCaveView()
        initClickCloseBtn()
    }

    private fun initSetNewCaveView() {
        getStorageData()
        setNickName()
    }

    private fun getStorageData() {
        val newCaveIntentData = intent.getParcelable(NEW_STORAGE, NewCaveIntent::class.java)
        with (binding) {
            tvSeeStorageTitle.text = newCaveIntentData?.name ?: ""
            tvSeeStorageSubTitle.text = newCaveIntentData?.introduction ?: ""
        }
    }

    private fun setNickName() {
        viewModel.getNickName()

        viewModel.nickName.observe(this) { nickName ->
            binding.tvSeeStorageUserNickname.text = nickName
        }
    }

    private fun initClickCloseBtn() {
        binding.btnStorageClose.setOnClickListener {
            finish()
        }
    }
}