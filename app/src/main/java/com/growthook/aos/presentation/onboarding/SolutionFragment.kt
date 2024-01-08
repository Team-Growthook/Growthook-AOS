package com.growthook.aos.presentation.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentSolutionBinding
import com.growthook.aos.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SolutionFragment : BaseFragment<FragmentSolutionBinding>() {

    private lateinit var callback: OnBackPressedCallback
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSolutionBinding = FragmentSolutionBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@SolutionFragment, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSolutionNext.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.anim_slide_in_from_right_fade_in,
                R.anim.anim_fade_out,
                R.anim.anim_slide_in_from_left_fade_in,
                R.anim.anim_fade_out,
            ).replace(R.id.fcv_onboarding_main, DescFragment()).addToBackStack(null).commit()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}
