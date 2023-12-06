package com.growthook.aos.presentation.cavedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentCaveSelectBottomsheetBinding
import com.growthook.aos.presentation.home.CaveSelectAdapter
import com.growthook.aos.util.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CaveDetailCaveSelectBottomSheet :
    BaseBottomSheetFragment<FragmentCaveSelectBottomsheetBinding>(R.layout.fragment_cave_select_bottomsheet) {
    private val viewModel by viewModels<CaveDetailCaveSelectViewModel>()
    private val caveDetailViewModel by activityViewModels<CaveDetailViewModel>()

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

        clickSelectBtn()
    }

    private fun setAdapter() {
        _adapter = CaveSelectAdapter()
        viewModel.getCaves()
        viewModel.caves.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.rcvHomeSelectCave.adapter = adapter

        val tracker = SelectionTracker.Builder<Long>(
            "caveSelection",
            binding.rcvHomeSelectCave,
            StableIdKeyProvider(binding.rcvHomeSelectCave),
            CaveSelectAdapter.InsightDetailsLookup(binding.rcvHomeSelectCave),
            StorageStrategy.createLongStorage(),
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything(),
        ).build()

        adapter.setSelectionTracker(tracker)

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()

                viewModel.selectedCave.value = adapter.getSelectedCave()
                Timber.d("동굴 ${adapter.getSelectedCave()}")
            }
        })
    }

    private fun clickSelectBtn() {
        binding.btnHomeSelectCave.setOnClickListener {
            lifecycleScope.launch {
                viewModel.selectedCave.collect {
                    dismiss()
                    Toast.makeText(
                        requireContext(),
                        "${it?.name}에 ${caveDetailViewModel.longClickInsight.value?.insightId}를 옮깁니다",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }
}
