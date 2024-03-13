package com.growthook.aos.presentation.todolist.completed

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
import com.growthook.aos.databinding.FragmentCompletedActionlistBinding
import com.growthook.aos.presentation.insight.actionplan.ActionplanInsightActivity
import com.growthook.aos.presentation.todolist.ReviewDetailActivity
import com.growthook.aos.presentation.todolist.TodolistViewModel
import com.growthook.aos.util.EmptyDataObserver
import com.growthook.aos.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CompletedActionlistFragment : BaseFragment<FragmentCompletedActionlistBinding>() {
    private var isScraped = false
    private var _completedActionlistAdapter: CompletedActionlistAdapter? = null
    private val completedActionlistAdapter
        get() = requireNotNull(_completedActionlistAdapter) { "completedActionlistAdapter is null" }

    private val viewModel by viewModels<CompletedActionlistViewModel>()
    private val todoViewModel by activityViewModels<TodolistViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCompletedActionlistBinding =
        FragmentCompletedActionlistBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDoneTodoList()
        initActionplanAdapter()
        subscribe()
        setEmptyView()
        clickScrapBtn()
    }

    private fun getDoneTodoList() {
        todoViewModel.getFinishedActionplans()
    }

    private fun initActionplanAdapter() {
        _completedActionlistAdapter =
            CompletedActionlistAdapter(::clickSeedDetail, ::clickReviewDetail, ::clickSeed)
        binding.rcvCompletedActionlist.adapter = _completedActionlistAdapter
        binding.rcvCompletedActionlist.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun clickSeedDetail(seedId: Int) {
        startActivity(
            ActionplanInsightActivity.getIntent(
                requireContext(),
                seedId,
                "CompletedActionlistFragment",
            ).addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION),
        )
    }

    private fun clickReviewDetail(actionplanId: Int) {
        startActivity(
            ReviewDetailActivity.getIntent(requireContext(), actionplanId)
                .addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION),
        )
    }

    private fun clickScrapBtn() {
        binding.clCompletedActionplanScrap.setOnClickListener {
            isScraped = !isScraped
            if (isScraped) {
                setScrapedTodo()
            } else {
                setUnScrapedTodo()
            }
        }
    }

    private fun setScrapedTodo() {
        binding.ivCompletedActionlistScrap.setImageResource(R.drawable.ic_home_scrap_true)
        binding.tvCompletedActionlistScrap.setTextColor(requireContext().getColor(R.color.Green200))
        todoViewModel.filterState.value = SCRAPED
    }

    private fun setUnScrapedTodo() {
        binding.ivCompletedActionlistScrap.setImageResource(R.drawable.ic_home_scrap_false)
        binding.tvCompletedActionlistScrap.setTextColor(requireContext().getColor(R.color.Gray100))
        todoViewModel.filterState.value = ALL
    }

    private fun subscribe() {
        observeActionplan()
        observeFilterState()
    }

    private fun observeActionplan() {
        todoViewModel.finishedActionplans.flowWithLifecycle(lifecycle).onEach { todo ->
            _completedActionlistAdapter?.submitList(todo)
        }.launchIn(lifecycleScope)
    }

    private fun observeFilterState() {
        todoViewModel.filterState.observe(viewLifecycleOwner) {
            todoViewModel.getFinishedActionplans()
        }
    }

    private fun setEmptyView() {
        _completedActionlistAdapter?.registerAdapterDataObserver(
            EmptyDataObserver(
                binding.rcvCompletedActionlist,
                binding.ivCompletedEmptyTodo,
                binding.tvCompletedEmptyTodo,
            ),
        )
    }

    private fun clickSeed(actionplanId: Int, isSeedSelected: Boolean) {
        viewModel.changeActionplanScrap(actionplanId)
        if (isSeedSelected) {
            Toast.makeText(requireContext(), "스크랩 완료!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        _completedActionlistAdapter = null
        super.onDestroyView()
    }

    companion object {
        const val SCRAPED = "scraped"
        const val ALL = "all"
    }
}
