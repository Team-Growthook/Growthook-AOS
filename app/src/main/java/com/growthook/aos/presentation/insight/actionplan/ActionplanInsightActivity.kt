package com.growthook.aos.presentation.insight.actionplan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.growthook.aos.databinding.ActivityActionplanInsightBinding
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.base.BaseAlertDialog
import timber.log.Timber

class ActionplanInsightActivity :
    BaseActivity<ActivityActionplanInsightBinding>({ ActivityActionplanInsightBinding.inflate(it) }) {
    private var _actionplanAdapter: ActionplanAdapter? = null
    private val actionplanAdapter
        get() = requireNotNull(_actionplanAdapter) { "actionplanAdapter is null" }

    private val viewModel by viewModels<ActionplanInsightViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foldInsightContent()
        observeActionplanData()
        initActionplanAdapter()
        clickeListeners()
    }

    private fun initActionplanAdapter() {
        _actionplanAdapter = ActionplanAdapter(::clickModifyMenu, ::clickDeleteMenu)
        binding.rcvActionplanInsight.adapter = _actionplanAdapter
    }

    private fun observeActionplanData() {
        viewModel.actionplanList.observe(this) {
            _actionplanAdapter?.submitList(it)
        }
    }

    private fun clickModifyMenu() {
        Timber.e("수정 바텀시트 구현해야함")
    }

    private fun clickDeleteMenu(position: Int) {
        BaseAlertDialog.Builder()
            .setCancelable(true)
            .build(
                type = BaseAlertDialog.DialogType.LEFT_INTENDED,
                title = "정말로 삭제할까요?",
                description = "삭제한 인사이트는 다시 복구할 수 없으니\n" + "신중하게 결정해 주세요!",
                positiveText = "유지하기",
                negativeText = "삭제하기",
                tipText = "",
                isBackgroundImageVisility = false,
                isDescriptionVisility = true,
                isRemainThookVisility = false,
                isTipVisility = false,
                negativeAction = {
                    // 삭제 로직 구현 필요
                    viewModel.deleteActionplan(position)
                    Toast.makeText(this, "액션이 삭제되었어요", Toast.LENGTH_SHORT).show()
                },
                positiveAction = {
                },
            ).show(supportFragmentManager, DELETE_DIALOG)
    }

    private fun foldInsightContent() {
        binding.clActionplanInsightCollapse.setOnClickListener {
            binding.clActionplanInsightCollapse.visibility = View.GONE
            binding.clActionplanInsightExpanded.visibility = View.VISIBLE
        }
        binding.clActionplanInsightExpanded.setOnClickListener {
            binding.clActionplanInsightExpanded.visibility = View.GONE
            binding.clActionplanInsightCollapse.visibility = View.VISIBLE
        }
    }

    private fun clickeListeners() {
        clickBackBtn()
        clickAddActionplan()
    }

    private fun clickBackBtn() {
        binding.ivActionplanInsightBack.setOnClickListener {
            finish()
        }
    }

    private fun clickAddActionplan() {
        binding.btnActionplanInsightAdd.setOnClickListener {
            // 액션플랜 더하기 바텀시트
        }
    }

    override fun onDestroy() {
        _actionplanAdapter = null
        super.onDestroy()
    }

    companion object {
        const val DELETE_DIALOG = "delete dialog"
    }
}
