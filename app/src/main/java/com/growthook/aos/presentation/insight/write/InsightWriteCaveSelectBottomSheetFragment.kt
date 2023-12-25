package com.growthook.aos.presentation.insight.write

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentInsightWriteCaveSelectBottomsheetBinding
import com.growthook.aos.util.base.BaseBottomSheetFragment

class InsightWriteCaveSelectBottomSheetFragment :
    BaseBottomSheetFragment<FragmentInsightWriteCaveSelectBottomsheetBinding>(R.layout.fragment_insight_write_cave_select_bottomsheet) {

    private val viewModel by activityViewModels<InsightWriteViewModel>()
    private var _caveAdapter: InsightWriteCaveSelectAdapter? = null
    private val caveAdapter
        get() = requireNotNull(_caveAdapter)

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentInsightWriteCaveSelectBottomsheetBinding =
        FragmentInsightWriteCaveSelectBottomsheetBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSetCaveAdapter()
    }

    private fun initSetCaveAdapter() {
        _caveAdapter = InsightWriteCaveSelectAdapter(::selectedCave)
        binding.rcvInsightWriteCave.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = caveAdapter
        }

        viewModel.mockCaveList.observe(viewLifecycleOwner) {
            caveAdapter.submitList(it)
        }
    }

    private fun selectedCave(caveItem: String) {
        binding.btnHomeSelectCave.setOnClickListener {
            Log.d("InsightWriteCave:", caveItem)
            viewModel.setSelectedCaveName(caveItem)
            dismiss()
        }
    }
}