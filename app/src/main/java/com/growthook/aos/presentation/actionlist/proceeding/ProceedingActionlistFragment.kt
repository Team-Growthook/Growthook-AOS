package com.growthook.aos.presentation.actionlist.proceeding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.growthook.aos.databinding.FragmentProceedingActionlistBinding
import com.growthook.aos.presentation.actionlist.ActionlistFragment
import com.growthook.aos.presentation.insight.actionplan.ActionplanInsightActivity
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.base.BaseFragment
import com.growthook.aos.util.base.BaseWritingBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProceedingActionlistFragment(private val parentFragment: ActionlistFragment) :
    BaseFragment<FragmentProceedingActionlistBinding>() {
    private var _proceedingActionlistAdapter: ProceedingActionlistAdapter? = null
    private val proceedingActionlistAdapter
        get() = requireNotNull(_proceedingActionlistAdapter) { "proceedingActionlistAdapter is null" }

    private val viewModel by viewModels<ProceedingActionlistViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentProceedingActionlistBinding =
        FragmentProceedingActionlistBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionplanAdapter()
        observeActionplan()
    }

    private fun initActionplanAdapter() {
        _proceedingActionlistAdapter =
            ProceedingActionlistAdapter(::selectedActionplanItem, ::clickCompleteBtn)
        binding.rcvProceedingActionlist.adapter = _proceedingActionlistAdapter
        binding.rcvProceedingActionlist.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeActionplan() {
        viewModel.doingActionplans.flowWithLifecycle(lifecycle).onEach { doingActionplan ->
            _proceedingActionlistAdapter?.submitList(doingActionplan)
        }.launchIn(lifecycleScope)
    }

    private fun clickCompleteBtn(actionplanId: Int) {
        BaseWritingBottomSheet.Builder().build(
            type = BaseWritingBottomSheet.WritingType.LARGE,
            title = "리뷰 작성",
            clickSaveBtn = {
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
                            viewModel.completeActionplan(actionplanId)
                            parentFragment.moveToCompletedActionTab()
                        },
                    ).show(parentFragmentManager, "get thook dialog")
            },
            clickNoWritingBtn = {
                viewModel.completeActionplan(actionplanId)
                parentFragment.moveToCompletedActionTab()
            },
        ).show(parentFragmentManager, "review complete dialog")
    }

    private fun selectedActionplanItem(seedId: Int) {
        val intent =
            Intent(requireActivity(), ActionplanInsightActivity::class.java)
        intent.putExtra("insightId", seedId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        _proceedingActionlistAdapter = null
        super.onDestroyView()
    }
}
