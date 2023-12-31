package com.growthook.aos.presentation.insight.noactionplan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.growthook.aos.databinding.ActivityNoActionplanInsightBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NoActionplanInsightActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoActionplanInsightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoActionplanInsightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListeners()

        val insightId = intent.getIntExtra("insightId", 0)
        Timber.d("인사이트 id $insightId")
    }

    private fun setClickListeners() {
        clickMenu()
        clickBackBtn()
    }

    private fun clickMenu() {
        binding.ivNoactionInsightToolbarMenu.setOnClickListener {
            val bottomSheetDialog = InsightMenuBottomsheet()
            bottomSheetDialog.show(supportFragmentManager, "show")
        }
    }

    private fun clickBackBtn() {
        binding.ivNoactionInsightBack.setOnClickListener {
            finish()
        }
    }
}
