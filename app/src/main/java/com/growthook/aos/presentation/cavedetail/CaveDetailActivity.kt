package com.growthook.aos.presentation.cavedetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.growthook.aos.databinding.ActivityCaveDetailBinding

class CaveDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCaveDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaveDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
