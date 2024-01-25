package com.growthook.aos.presentation.actionlist.completed

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
import com.growthook.aos.presentation.actionlist.ReviewDetailActivity
import com.growthook.aos.presentation.home.HomeViewModel
import com.growthook.aos.presentation.insight.actionplan.ActionplanInsightActivity
import com.growthook.aos.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class CompletedActionlistFragment : BaseFragment<FragmentCompletedActionlistBinding>() {
    private var isScraped = false
    private var _completedActionlistAdapter: CompletedActionlistAdapter? = null
    private val completedActionlistAdapter
        get() = requireNotNull(_completedActionlistAdapter) { "completedActionlistAdapter is null" }

    private val viewModel by viewModels<CompletedActionlistViewModel>()
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCompletedActionlistBinding =
        FragmentCompletedActionlistBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getActionplanPercent()
        initActionplanAdapter()
        observeActionplan()
        clickScrapBtn()
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
            ),
        )
    }

    private fun clickReviewDetail(actionplanId: Int) {
        startActivity(ReviewDetailActivity.getIntent(requireContext(), actionplanId))
    }

    private fun clickScrapBtn() {
        binding.clCompletedActionplanScrap.setOnClickListener {
            isScraped = !isScraped
            if (isScraped) {
                binding.ivCompletedActionlistScrap.setImageResource(R.drawable.ic_home_scrap_true)
                binding.tvCompletedActionlistScrap.setTextColor(requireContext().getColor(R.color.Green200))
                viewModel.getScrapedActionplan()
            } else {
                binding.ivCompletedActionlistScrap.setImageResource(R.drawable.ic_home_scrap_false)
                binding.tvCompletedActionlistScrap.setTextColor(requireContext().getColor(R.color.White000))
                viewModel.getFinishedActionplans()
            }
        }
    }

    private fun observeActionplan() {
        viewModel.finishedActionplans.flowWithLifecycle(lifecycle).onEach { doingActionplan ->
            Timber.w("doingActionplan:: $doingActionplan")
            _completedActionlistAdapter?.submitList(doingActionplan)
        }.launchIn(lifecycleScope)
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
}
