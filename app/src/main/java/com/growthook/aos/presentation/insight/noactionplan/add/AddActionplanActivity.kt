package com.growthook.aos.presentation.insight.noactionplan.add

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivityAddActionplanBinding
import com.growthook.aos.presentation.insight.actionplan.ActionplanInsightActivity
import com.growthook.aos.presentation.insight.noactionplan.add.AddActionplanViewModel.Event
import com.growthook.aos.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddActionplanActivity :
    BaseActivity<ActivityAddActionplanBinding>({ ActivityAddActionplanBinding.inflate(it) }) {
    private var _addActionplanAdapter: AddActionplanAdapter? = null
    private val addActionplanAdapter
        get() = requireNotNull(_addActionplanAdapter) { "addActionplanAdapter is null" }
    private val viewModel by viewModels<AddActionplanViewModel>()
    private var seedId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSeedIdFromSeedDetail()
        foldInsightContent()
        initEditTextAdapter()
        subscribe()
        clickListeners()
    }

    private fun getSeedIdFromSeedDetail() {
        seedId = intent.getIntExtra(SEED_ID, 0)
        Timber.d("AddActionplanActivity seed id $seedId")
    }

    private fun subscribe() {
        observeActionplanList()
        observeButtonEnabled()
        observePostEvent()
    }

    private fun clickListeners() {
        clickBackBtn()
        clickPlusBtn()
        clickCompleteBtn()
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

    private fun observeActionplanList() {
        viewModel.actionplanList.observe(this) { actionplans ->
            Timber.e("actionplanList size:: ${actionplans.size}")
            Timber.e("actionplanList content:: $actionplans")
            val isActionplanEmpty = actionplans.all { it.isBlank() }
            viewModel.isButtonEnabled.value = !isActionplanEmpty
        }
    }

    private fun observeButtonEnabled() {
        viewModel.isButtonEnabled.observe(this) { isEnabled ->
            if (isEnabled) {
                binding.tvAddActionplanComplete.setTextColor(Color.parseColor("#23B877"))
                binding.tvAddActionplanComplete.isClickable = true
            } else {
                binding.tvAddActionplanComplete.setTextColor(Color.parseColor("#6B6E82"))
                binding.tvAddActionplanComplete.isClickable = false
            }
        }
    }

    private fun clickBackBtn() {
        binding.ivAddActionplanBack.setOnClickListener {
            finish()
        }
    }

    private fun clickPlusBtn() {
        binding.ivAddActionplanPlus.setOnClickListener {
            _addActionplanAdapter?.addItem("")
        }
    }

    private fun clickCompleteBtn() {
        binding.tvAddActionplanComplete.setOnClickListener {
            viewModel.actionplanList.value?.let { actionplans ->
                viewModel.postActionplans(seedId, actionplans)
            }
        }
    }

    private fun observePostEvent() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is Event.PostSuccess -> {
                    startActivity(ActionplanInsightActivity.getIntent(this, seedId))
                }

                is Event.PostFailed -> {
                    Toast.makeText(this, "업로드에 실패했습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        _addActionplanAdapter = null
        super.onDestroy()
    }

    companion object {
        private const val SEED_ID = "seedId"
        fun getIntent(context: Context, seedId: Int): Intent {
            return Intent(context, AddActionplanActivity::class.java).apply {
                putExtra(SEED_ID, seedId)
            }
        }
    }
}
