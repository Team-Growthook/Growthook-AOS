package com.growthook.aos.presentation.mypage.detailinfo

import android.os.Bundle
import com.growthook.aos.databinding.ActivityDetailMyPageBinding
import com.growthook.aos.util.base.BaseActivity

class DetailMyPageActivity :
    BaseActivity<ActivityDetailMyPageBinding>({ ActivityDetailMyPageBinding.inflate(it) }) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clickBackNavi()
    }

    private fun clickBackNavi() {
        binding.tbMyPageDetail.setNavigationOnClickListener {
            finish()
        }
    }
}
