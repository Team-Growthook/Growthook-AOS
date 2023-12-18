package com.growthook.aos.presentation.actionlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.growthook.aos.databinding.FragmentActionlistBinding
import com.growthook.aos.presentation.home.HomeViewModel
import com.growthook.aos.util.base.BaseFragment

class ActionlistFragment : BaseFragment<FragmentActionlistBinding>() {
    private val viewModel: HomeViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentActionlistBinding = FragmentActionlistBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitleText()
    }

    private fun setTitleText() {
        viewModel.nickname.observe(viewLifecycleOwner) { nickName ->
            binding.tvActionlistTitle.text = "${nickName}님의 액션"
        }
    }
}
