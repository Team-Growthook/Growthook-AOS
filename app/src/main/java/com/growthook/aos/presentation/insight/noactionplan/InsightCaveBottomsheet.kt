package com.growthook.aos.presentation.insight.noactionplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentInsightCaveBottomsheetBinding
import com.growthook.aos.util.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        getCaves()
        observeCaveData()
        initAdapter()
        clickBottomBtn()
    }

    private fun initAdapter() {
        _insightCaveAdapter = InsightCaveAdapter(::clickCaveItem)
        binding.rcvInsightCave.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = _insightCaveAdapter
        }
    }

    private fun getCaves() {
        viewModel.getCaves()
    }

    private fun observeCaveData() {
        viewModel.caves.observe(viewLifecycleOwner) {
            _insightCaveAdapter?.submitList(it)
        }
    }

    private fun clickCaveItem(caveName: String) {
        // TODO 뷰모델에 선택한 아이템 정보 담기
    }

    private fun clickBottomBtn() {
        binding.btnInsightCave.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                delay(100)
                dismiss()
            }
            Toast.makeText(context, "씨앗을 옮겨 심었어요", Toast.LENGTH_SHORT).show()
        }
    }
}
