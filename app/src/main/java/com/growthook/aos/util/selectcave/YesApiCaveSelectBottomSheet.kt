package com.growthook.aos.util.selectcave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.growthook.aos.databinding.FragmentCaveSelectBottomsheetBinding
import com.growthook.aos.util.EmptyDataObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class YesApiCaveSelectBottomSheet : CaveSelect() {
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
        moveSeed()
    }

    private fun moveSeed() {
        lifecycleScope.launch {
            viewModel.selectedCave.collect { cave ->
                binding.btnHomeSelectCave.setOnClickListener {
                    cave?.let {
                        viewModel.moveSeed(toMoveSeedId, it.id)
                        viewModel.isMove.observe(viewLifecycleOwner) { isMove ->
                            if (isMove) {
                                dismiss()
                                Toast.makeText(requireContext(), "씨앗을 옮겨 심었어요", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                    viewModel.caves.value?.let { caves ->
                        if (caves.isEmpty()) {
                            dismiss()
                        }
                    }
                }
            }
        }
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
        adapter.registerAdapterDataObserver(
            EmptyDataObserver(
                binding.rcvHomeSelectCave,
                binding.ivCaveSelectEmpty,
                binding.tvCaveSelectEmpty,
            ),
        )

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()

                viewModel.selectedCave.value = adapter.getSelectedCave()
                Timber.d("동굴 ${adapter.getSelectedCave()}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }
}
