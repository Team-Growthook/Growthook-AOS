package com.growthook.aos.presentation.cavedetail.cavemodify

import android.os.Bundle
import com.growthook.aos.databinding.ActivityCaveDetailModifyBinding
import com.growthook.aos.util.base.BaseActivity
import timber.log.Timber

class CaveDetailModifyActivity :
    BaseActivity<ActivityCaveDetailModifyBinding>({ ActivityCaveDetailModifyBinding.inflate(it) }) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("동굴 수정 ${intent.getStringExtra("caveName")}")
    }
}
