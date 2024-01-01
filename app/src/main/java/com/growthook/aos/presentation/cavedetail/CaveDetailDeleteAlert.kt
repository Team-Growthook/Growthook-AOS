package com.growthook.aos.presentation.cavedetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.growthook.aos.databinding.DialogLeftIntendedBinding
import com.growthook.aos.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CaveDetailDeleteAlert : DialogFragment() {

    private var _binding: DialogLeftIntendedBinding? = null
    private val binding: DialogLeftIntendedBinding
        get() = requireNotNull(_binding) { "binding is null" }

    private val viewModel: CaveDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogLeftIntendedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setText()
        clickLeftBtn()
        clickRightBtn()
    }

    private fun setText() {
        binding.tvLeftIntendedTitle.text = "정말로 삭제할까요?"
        binding.tvLeftIntendedDesc.text = "삭제한 보관함은 다시 복구할 수 없으니\n" +
            "신중하게 결정해 주세요!"
        binding.tvLeftIntendedLeft.text = "유지하기"
        binding.tvLeftIntendedRight.text = "삭제하기"
    }

    private fun clickLeftBtn() {
        binding.tvLeftIntendedLeft.setOnClickListener {
            dismiss()
        }
    }

    private fun clickRightBtn() {
        binding.tvLeftIntendedRight.setOnClickListener {
            deleteCave()
            afterDeleteSuccess()
        }
    }

    private fun deleteCave() {
        lifecycleScope.launch {
            viewModel.caveId.collect {
                if (it != 0) {
                    viewModel.deleteCave(it)
                }
            }
        }
    }

    private fun afterDeleteSuccess() {
        viewModel.isDelete.observe(viewLifecycleOwner) { isDelete ->
            if (isDelete) {
                Toast.makeText(requireContext(), "동굴이 삭제되었어요", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
