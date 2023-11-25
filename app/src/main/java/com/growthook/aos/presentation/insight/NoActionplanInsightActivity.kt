package com.growthook.aos.presentation.insight

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.growthook.aos.databinding.ActivityNoActionplanInsightBinding

class NoActionplanInsightActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoActionplanInsightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoActionplanInsightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListeners()
    }

    private fun setClickListeners() {
        clickMenu()
    }

    private fun clickMenu() {
        binding.ivNoactionInsightToolbarMenu.setOnClickListener {
            val bottomSheetDialog = InsightMenuBottomsheet()
            bottomSheetDialog.show(supportFragmentManager, "show")
        }
    }
}
