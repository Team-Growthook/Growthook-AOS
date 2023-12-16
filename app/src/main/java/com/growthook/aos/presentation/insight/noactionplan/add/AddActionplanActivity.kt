package com.growthook.aos.presentation.insight.noactionplan.add

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivityAddActionplanBinding
import com.growthook.aos.util.base.BaseActivity
import timber.log.Timber

class AddActionplanActivity :
    BaseActivity<ActivityAddActionplanBinding>({ ActivityAddActionplanBinding.inflate(it) }) {
    private var _addActionplanAdapter: AddActionplanAdapter? = null
    private val addActionplanAdapter
        get() = requireNotNull(_addActionplanAdapter) { "addActionplanAdapter is null" }
    private val viewModel by viewModels<AddActionplanViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foldInsightContent()
        initEditTextAdapter()
        observeActionplanList()
        observeButtonEnabled()
        clickPlusBtn()
    }

    private fun foldInsightContent() {
        binding.clAddActionplanCollapse.setOnClickListener {
            binding.clAddActionplanCollapse.visibility = View.GONE
            binding.clAddActionplanExpanded.visibility = View.VISIBLE
        }
        binding.clAddActionplanExpanded.setOnClickListener {
            binding.clAddActionplanExpanded.visibility = View.GONE
            binding.clAddActionplanCollapse.visibility = View.VISIBLE
        }
    }

    private fun initEditTextAdapter() {
        _addActionplanAdapter = AddActionplanAdapter(
            list = viewModel.actionplanList.value ?: mutableListOf(""),
            onAddItem = { viewModel.addItem("") },
            onEditTextChanged = { position, text -> viewModel.updateItem(position, text) },
        )
        binding.rcvAddActionplanEdittext.adapter = _addActionplanAdapter
    }

    private fun clickPlusBtn() {
        binding.ivAddActionplanPlus.setOnClickListener {
            _addActionplanAdapter?.addItem("")
        }
    }

    private fun observeActionplanList() {
        viewModel.actionplanList.observe(this) { actionplans ->
            Timber.e("actionplanList size:: ${actionplans.size}")
            Timber.e("actionplanList content:: $actionplans")
            val isActionplanEmpty = actionplans.any { it.isBlank() }
            viewModel.isButtonEnabled.value = !isActionplanEmpty && actionplans.size >= 1
        }
    }

    private fun observeButtonEnabled() {
        viewModel.isButtonEnabled.observe(this) { isEnabled ->
            if (isEnabled) {
                binding.tvAddActionplanComplete.setTextColor(Color.parseColor("#23B877"))
            } else {
                binding.tvAddActionplanComplete.setTextColor(Color.parseColor("#6B6E82"))
            }
        }
    }

    override fun onDestroy() {
        _addActionplanAdapter = null
        super.onDestroy()
    }
}
