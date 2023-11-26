package com.growthook.aos.presentation.insight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentInsightCaveBottomsheetBinding
import com.growthook.aos.util.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightCaveBottomsheet :
    BaseBottomSheetFragment<FragmentInsightCaveBottomsheetBinding>(R.layout.fragment_insight_cave_bottomsheet) {
    private val viewModel by activityViewModels<NoActionplanInsightViewModel>()
    private var _insightCaveAdapter: InsightCaveAdapter? = null

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentInsightCaveBottomsheetBinding =
        FragmentInsightCaveBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _insightCaveAdapter = InsightCaveAdapter()
        initAdapter()
        viewModel.caveList.observe(viewLifecycleOwner) {
            _insightCaveAdapter?.submitList(it)
        }
    }

    private fun initAdapter() {
        binding.rcvInsightCave.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = _insightCaveAdapter
        }
    }

    /*
    val chooseProjectAdapter = HomeChooseProjectAdapter(::clickProjectItem)

        binding.rcvHomeChooseProject.adapter = chooseProjectAdapter
        chooseProjectAdapter.submitList(viewModel.projectList.value)
     */

    private fun observeCaveData() {
        viewModel.caveList.observe(viewLifecycleOwner) {
            _insightCaveAdapter?.submitList(it)
        }
    }
}
