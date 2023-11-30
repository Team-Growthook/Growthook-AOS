package com.growthook.aos.presentation.storage

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityCreateStorageBinding
import com.growthook.aos.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateStorageActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCreateStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}