package com.growthook.aos.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentCaveSelectBottomsheetBinding
import com.growthook.aos.util.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaveSelectBottomSheet :
    BaseBottomSheetFragment<FragmentCaveSelectBottomsheetBinding>(R.layout.fragment_cave_select_bottomsheet) {
    private val viewModel by viewModels<CaveSelectBottomSheetViewModel>()

    private var _adapter: CaveSelectAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { "adapter is null" }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCaveSelectBottomsheetBinding =
        FragmentCaveSelectBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
    }

    private fun setAdapter() {
        _adapter = CaveSelectAdapter()
        binding.rcvHomeSelectCave.adapter = adapter
        viewModel.getCaves()
        viewModel.caves.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }
}
