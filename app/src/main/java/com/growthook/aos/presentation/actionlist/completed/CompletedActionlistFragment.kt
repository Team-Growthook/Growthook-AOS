package com.growthook.aos.presentation.actionlist.completed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.growthook.aos.databinding.FragmentCompletedActionlistBinding
import com.growthook.aos.presentation.insight.actionplan.ActionplanInsightActivity
import com.growthook.aos.util.base.BaseFragment

class CompletedActionlistFragment : BaseFragment<FragmentCompletedActionlistBinding>() {
    private var _completedActionlistAdapter: CompletedActionlistAdapter? = null
    private val completedActionlistAdapter
        get() = requireNotNull(_completedActionlistAdapter) { "completedActionlistAdapter is null" }

    private val viewModel by viewModels<CompletedActionlistViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCompletedActionlistBinding =
        FragmentCompletedActionlistBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionplanAdapter()
        observeActionplan()
    }

    private fun initActionplanAdapter() {
        _completedActionlistAdapter = CompletedActionlistAdapter(::clickSeedDetail)
        binding.rcvCompletedActionlist.adapter = _completedActionlistAdapter
        binding.rcvCompletedActionlist.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeActionplan() {
        viewModel.completedActionplanList.observe(viewLifecycleOwner) {
            _completedActionlistAdapter?.submitList(it)
        }
    }

    private fun clickSeedDetail(seedId: Int) {
        val intent =
            Intent(requireActivity(), ActionplanInsightActivity::class.java)
        intent.putExtra("insightId", seedId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        _completedActionlistAdapter = null
        super.onDestroyView()
    }
}
