package com.growthook.aos.presentation.storage

import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityCreateStorageBinding
import com.growthook.aos.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateStorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMakeCreateStorageView()
    }

    private fun initMakeCreateStorageView() {
        changeTextLayout()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard() {
        val currentFocus = currentFocus
        if (currentFocus is EditText) {
            currentFocus.clearFocus()
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0)
        }
    }

    private fun changeTextLayout() {
        //
    }
}