package com.growthook.aos.presentation.actionlist.proceeding

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
import com.growthook.aos.presentation.actionlist.ActionlistFragment
import com.growthook.aos.presentation.actionlist.proceeding.ProceedingActionlistViewModel.Event
import com.growthook.aos.presentation.home.HomeViewModel
import com.growthook.aos.presentation.insight.actionplan.ActionplanInsightActivity
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.base.BaseFragment
import com.growthook.aos.util.base.BaseWritingBottomSheet
import com.growthook.aos.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class ProceedingActionlistFragment(private val parentFragment: ActionlistFragment) :
    BaseFragment<FragmentProceedingActionlistBinding>() {
    private var isScraped = false
    private var _proceedingActionlistAdapter: ProceedingActionlistAdapter? = null
    private val proceedingActionlistAdapter
        get() = requireNotNull(_proceedingActionlistAdapter) { "proceedingActionlistAdapter is null" }

    private val viewModel by viewModels<ProceedingActionlistViewModel>()
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentProceedingActionlistBinding =
        FragmentProceedingActionlistBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionplanAdapter()
        observeActionplan()
        observeEvent()
        clickScrapBtn()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDoingActionplans()
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

    private fun observeActionplan() {
        viewModel.doingActionplans.flowWithLifecycle(lifecycle).onEach { doingActionplan ->
            Timber.w("doingActionplan:: $doingActionplan")
            _proceedingActionlistAdapter?.submitList(doingActionplan)
        }.launchIn(lifecycleScope)
    }

    private fun clickScrapBtn() {
        binding.clProceedingActionplanScrap.setOnSingleClickListener {
            isScraped = !isScraped
            if (isScraped) {
                binding.ivProceedingActionlistScrap.setImageResource(R.drawable.ic_home_scrap_true)
                binding.tvProceedingActionlistScrap.setTextColor(requireContext().getColor(R.color.Green200))
                viewModel.getScrapedActionplan()
            } else {
                binding.ivProceedingActionlistScrap.setImageResource(R.drawable.ic_home_scrap_false)
                binding.tvProceedingActionlistScrap.setTextColor(requireContext().getColor(R.color.White000))
                viewModel.getDoingActionplans()
            }
        }
    }

    private fun clickCompleteBtn(actionplanId: Int) {
        BaseWritingBottomSheet.Builder().build(
            type = BaseWritingBottomSheet.WritingType.LARGE,
            title = "리뷰 작성",
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
            ),
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
}
