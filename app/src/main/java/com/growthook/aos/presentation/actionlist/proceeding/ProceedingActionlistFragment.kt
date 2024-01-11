package com.growthook.aos.presentation.actionlist.proceeding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentProceedingActionlistBinding
import com.growthook.aos.presentation.actionlist.completed.CompletedActionlistFragment
import com.growthook.aos.presentation.insight.actionplan.ActionplanInsightActivity
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.base.BaseFragment
import com.growthook.aos.util.base.BaseWritingBottomSheet

class ProceedingActionlistFragment : BaseFragment<FragmentProceedingActionlistBinding>() {
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
        viewModel.proceedingActionplanList.observe(viewLifecycleOwner) {
            _proceedingActionlistAdapter?.submitList(it)
        }
    }

    private fun clickCompleteBtn() {
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
                            val transaction = parentFragmentManager.beginTransaction()
                            val completedFragment = CompletedActionlistFragment()
                            transaction.show(completedFragment)
                            parentFragmentManager.commit {
                                val tag = CompletedActionlistFragment::class.java.simpleName
                                replace(R.id.fcv_actionlist_main, completedFragment, tag)
                            }
                        },
                    ).show(parentFragmentManager, "get thook dialog")
            },
            clickNoWritingBtn = {},
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
