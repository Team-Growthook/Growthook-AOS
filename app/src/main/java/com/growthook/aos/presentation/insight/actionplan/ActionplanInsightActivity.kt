package com.growthook.aos.presentation.insight.actionplan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityActionplanInsightBinding
import com.growthook.aos.presentation.MainActivity
import com.growthook.aos.presentation.insight.actionplan.ActionplanInsightViewModel.Event
import com.growthook.aos.util.base.BaseActivity
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.base.BaseWritingBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class ActionplanInsightActivity :
    BaseActivity<ActivityActionplanInsightBinding>({ ActivityActionplanInsightBinding.inflate(it) }) {
    private var _actionplanAdapter: ActionplanAdapter? = null
    private val actionplanAdapter
        get() = requireNotNull(_actionplanAdapter) { "actionplanAdapter is null" }

    private val viewModel by viewModels<ActionplanInsightViewModel>()
    private var seedId: Int = 0
    private var previousView: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentValue()
        setVisibilityBtn()
        initActionplanAdapter()
        subscribe()
        foldInsightContent()
        clickeListeners()
    }

    private fun getIntentValue() {
        seedId = intent.getIntExtra(SEED_ID, 0)
        previousView = intent.getStringExtra(PRE_STATE) ?: "nulll"
        Timber.d("ActionplanInsightActivity seed id $seedId")
        viewModel.seedId = seedId
        viewModel.getSeedDetail()
        viewModel.getActionplans()
    }

    private fun setVisibilityBtn() {
        when (previousView) {
            "AddActionplanActivity" -> {
                binding.ivActionplanInsightClose.visibility = View.VISIBLE
                binding.ivActionplanInsightBack.visibility = View.INVISIBLE
            }
        }
    }

    private fun subscribe() {
        observeSeedDetail()
        observeActionplanData()
        observeEvent()
    }

    private fun initActionplanAdapter() {
        _actionplanAdapter =
            ActionplanAdapter(
                ::clickModifyMenu,
                ::clickDeleteMenu,
                ::clickCompleteBtn,
                ::clickScrapActionplan,
                ::isScraped,
            )
        binding.rcvActionplanInsight.adapter = _actionplanAdapter
    }

    private fun observeActionplanData() {
        viewModel.actionplans.flowWithLifecycle(lifecycle).onEach { actionplans ->
            _actionplanAdapter?.submitList(actionplans)
            _actionplanAdapter?.notifyDataSetChanged()
        }.launchIn(lifecycleScope)
    }

    private fun clickCompleteBtn(actionplanId: Int) {
        BaseWritingBottomSheet.Builder().build(
            type = BaseWritingBottomSheet.WritingType.LARGE,
            title = "리뷰 작성",
            clickSaveBtn = {
                viewModel.postReview(actionplanId, it)
                viewModel.completeActionplan(actionplanId)
                viewModel.getActionplans()
            },
            clickNoWritingBtn = {
                viewModel.completeActionplan(actionplanId)
                viewModel.getActionplans()
            },
        ).show(supportFragmentManager, "review dialog")
    }

    private fun clickModifyMenu(actionplanId: Int) {
        BaseWritingBottomSheet.Builder().build(
            type = BaseWritingBottomSheet.WritingType.SMALL,
            title = "액션플랜 수정",
            clickSaveBtn = {
                viewModel.modifyActionplan(actionplanId, it)
            },
            clickNoWritingBtn = {
            },
        ).show(supportFragmentManager, "actionplan modify dialog")
    }

    private fun clickDeleteMenu(actionplanId: Int) {
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
                    viewModel.deleteActionplan(actionplanId)
                },
                positiveAction = {
                },
            ).show(supportFragmentManager, DELETE_DIALOG)
    }

    private fun observeSeedDetail() {
        viewModel.seedData.observe(this) { seed ->
            with(binding) {
                tvActionplanInsightTitle.text = seed?.title
                tvActionplanInsightContent.text = seed?.content
                tvActionplanInsightDate.text = seed?.date
                tvActionplanInsightChip.text = seed?.caveName
                "D-${seed?.remainingDays}".also { tvActionplanInsightDday.text = it }

                if (seed.content == null) {
                    clActionplanInsightMemoEmpty.visibility = View.VISIBLE
                    scvActionplanInsightContent.visibility = View.INVISIBLE
                } else {
                    clActionplanInsightMemoEmpty.visibility = View.GONE
                    scvActionplanInsightContent.visibility = View.VISIBLE
                }

                if (seed.isScraped) {
                    ivActionplanInsightSeed.setImageResource(R.drawable.ic_scrap_selected)
                } else {
                    ivActionplanInsightSeed.setImageResource(R.drawable.ic_scrap_unselected)
                }
            }
        }
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
        clickCloseBtn()
    }

    private fun clickCloseBtn() {
        binding.ivActionplanInsightClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun clickBackBtn() {
        binding.ivActionplanInsightBack.setOnClickListener {
            finish()
        }
    }

    private fun clickScrapActionplan(actionplanId: Int) {
        viewModel.changeScrap(actionplanId)
    }

    private fun clickAddActionplan() {
        binding.btnActionplanInsightAdd.setOnClickListener {
            BaseWritingBottomSheet.Builder().build(
                type = BaseWritingBottomSheet.WritingType.SMALL,
                title = "액션 플랜 추가",
                clickSaveBtn = {
                    viewModel.postActionplan(seedId, it)
                },
                clickNoWritingBtn = {},
            ).show(supportFragmentManager, "add actionplan dialog")
        }
    }

    private fun isScraped(): Boolean {
        var isScraped = false
        viewModel.event.flowWithLifecycle(lifecycle).onEach { event ->
            if (event == Event.ScrapSuccess) {
                isScraped = true
            }
        }.launchIn(lifecycleScope)
        return isScraped
    }

    private fun observeEvent() {
        viewModel.event.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Event.PostActionplanSuccess -> {
                    Toast.makeText(this, "액션을 만들었어요!", Toast.LENGTH_SHORT).show()
                }

                is Event.ModifySuccess -> {
                    Toast.makeText(this, "액션이 수정되었어요", Toast.LENGTH_SHORT).show()
                }

                is Event.DeleteSuccess -> {
                    Toast.makeText(this, "액션이 삭제되었어요", Toast.LENGTH_SHORT).show()
                }

                is Event.PostReviewSuccess -> {
                    BaseAlertDialog.Builder()
                        .setCancelable(false)
                        .build(
                            type = BaseAlertDialog.DialogType.SINGLE_INTENDED,
                            title = "성장의 보상으로\n쑥을 얻었어요!",
                            description = "한 단계 쑥! 성장한 것을 축하해요.\n수확한 쑥을 통해\n씨앗의 잠금을 해제해보세요:)",
                            isTipVisility = false,
                            isRemainThookVisility = false,
                            isBackgroundImageVisility = true,
                            isDescriptionVisility = true,
                            positiveText = "확인",
                            negativeText = "",
                            tipText = "",
                            negativeAction = {},
                            positiveAction = {},
                        ).show(supportFragmentManager, "get thook dialog")
                }

                is Event.ScrapSuccess -> {
                    Toast.makeText(this, "액션플랜 스크랩 완료!", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        _actionplanAdapter = null
        super.onDestroy()
    }

    companion object {
        const val DELETE_DIALOG = "delete dialog"
        private const val TAG = "tag"
        private const val SEED_ID = "seedId"
        private const val PRE_STATE = "preState"

        fun getIntent(context: Context, seedId: Int, preState: String): Intent {
            return Intent(context, ActionplanInsightActivity::class.java).apply {
                putExtra(SEED_ID, seedId)
                putExtra(PRE_STATE, preState)
            }
        }
    }
}
