package com.growthook.aos.presentation.todolist.proceeding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentProceedingActionlistBinding
import com.growthook.aos.presentation.insight.actionplan.ActionplanInsightActivity
import com.growthook.aos.presentation.todolist.TodolistFragment
import com.growthook.aos.presentation.todolist.TodolistViewModel
import com.growthook.aos.presentation.todolist.proceeding.ProceedingActionlistViewModel.Event
import com.growthook.aos.util.EmptyDataObserver
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.base.BaseFragment
import com.growthook.aos.util.base.BaseWritingBottomSheet
import com.growthook.aos.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProceedingActionlistFragment(private val parentFragment: TodolistFragment) :
    BaseFragment<FragmentProceedingActionlistBinding>() {
    private var isScraped = false
    private var _proceedingActionlistAdapter: ProceedingActionlistAdapter? = null
    private val proceedingActionlistAdapter
        get() = requireNotNull(_proceedingActionlistAdapter) { "proceedingActionlistAdapter is null" }

    private val viewModel by viewModels<ProceedingActionlistViewModel>()
    private val todoViewModel by activityViewModels<TodolistViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentProceedingActionlistBinding =
        FragmentProceedingActionlistBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDoingTodoList()
        initActionplanAdapter()
        subscribe()
        setEmptyView()
        clickScrapBtn()
    }

    override fun onResume() {
        super.onResume()
        todoViewModel.getDoingActionplans()
    }

    private fun getDoingTodoList() {
        todoViewModel.getDoingActionplans()
    }

    private fun initActionplanAdapter() {
        _proceedingActionlistAdapter =
            ProceedingActionlistAdapter(
                ::clickSeedDetail,
                ::clickCompleteBtn,
                ::clickScrapBtn,
                ::clickSeed,
            )
        binding.rcvProceedingActionlist.adapter = _proceedingActionlistAdapter
        binding.rcvProceedingActionlist.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribe() {
        observeFilterState()
        observeActionplan()
        observeEvent()
    }

    private fun observeFilterState() {
        todoViewModel.filterState.observe(viewLifecycleOwner) {
            todoViewModel.getDoingActionplans()
        }
    }

    private fun observeActionplan() {
        todoViewModel.doingActionplans.flowWithLifecycle(lifecycle).onEach { doingActionplan ->
            _proceedingActionlistAdapter?.submitList(doingActionplan)
        }.launchIn(lifecycleScope)
    }

    private fun setEmptyView() {
        _proceedingActionlistAdapter?.registerAdapterDataObserver(
            EmptyDataObserver(
                binding.rcvProceedingActionlist,
                binding.ivProceedingEmptyTodo,
                binding.tvProceedingEmptyTodo,
            ),
        )
    }

    private fun clickScrapBtn() {
        binding.clProceedingActionplanScrap.setOnSingleClickListener {
            isScraped = !isScraped
            if (isScraped) {
                setScrapedTodo()
            } else {
                setUnScrapedTodo()
            }
        }
    }

    private fun setScrapedTodo() {
        binding.ivProceedingActionlistScrap.setImageResource(R.drawable.ic_home_scrap_true)
        binding.tvProceedingActionlistScrap.setTextColor(requireContext().getColor(R.color.Green200))
        todoViewModel.filterState.value = SCRAPED
    }

    private fun setUnScrapedTodo() {
        binding.ivProceedingActionlistScrap.setImageResource(R.drawable.ic_home_scrap_false)
        binding.tvProceedingActionlistScrap.setTextColor(requireContext().getColor(R.color.Gray100))
        todoViewModel.filterState.value = ALL
    }

    private fun clickCompleteBtn(actionplanId: Int) {
        BaseWritingBottomSheet.Builder().build(
            type = BaseWritingBottomSheet.WritingType.LARGE,
            title = "느낀점 작성",
            hint = "할 일을 달성하며 어떤 것을 느꼈는지 작성해보세요",
            clickSaveBtn = {
                viewModel.postReview(actionplanId, it)
                viewModel.completeActionplan(actionplanId)
            },
            clickNoWritingBtn = {
                viewModel.completeActionplan(actionplanId)
            },
        ).show(parentFragmentManager, "review complete dialog")
    }

    private fun clickSeedDetail(seedId: Int) {
        startActivity(
            ActionplanInsightActivity.getIntent(
                requireContext(),
                seedId,
                "ProceedingActionlistFragment",
            ).addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION),
        )
    }

    private fun observeEvent() {
        viewModel.event.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Event.PostCompletedActionplanSuccess -> {
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
                            positiveAction = {
                                parentFragment.moveToCompletedActionTab()
                            },
                            remainThookText = "",
                        ).show(parentFragmentManager, "get thook dialog")
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun clickSeed(actionplanId: Int, isSeedSelected: Boolean) {
        viewModel.changeActionplanScrap(actionplanId)
        if (isSeedSelected) {
            Toast.makeText(requireContext(), "스크랩 완료!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        _proceedingActionlistAdapter = null
        super.onDestroyView()
    }

    companion object {
        const val SCRAPED = "scraped"
        const val ALL = "all"
    }
}
