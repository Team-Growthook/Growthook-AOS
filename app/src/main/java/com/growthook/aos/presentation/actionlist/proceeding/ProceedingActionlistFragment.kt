package com.growthook.aos.presentation.actionlist.proceeding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.growthook.aos.databinding.FragmentProceedingActionlistBinding
import com.growthook.aos.presentation.insight.actionplan.ActionplanInsightActivity
import com.growthook.aos.util.base.BaseFragment

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
        clickCompleteBtn()
    }

    private fun initActionplanAdapter() {
        _proceedingActionlistAdapter = ProceedingActionlistAdapter(::selectedActionplanItem)
        binding.rcvProceedingActionlist.adapter = _proceedingActionlistAdapter
        binding.rcvProceedingActionlist.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeActionplan() {
        viewModel.proceedingActionplanList.observe(viewLifecycleOwner) {
            _proceedingActionlistAdapter?.submitList(it)
        }
    }

    private fun clickCompleteBtn() {
        // 리뷰 작성 바텀시트
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
