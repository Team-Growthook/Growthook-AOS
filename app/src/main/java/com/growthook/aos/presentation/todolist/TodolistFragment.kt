package com.growthook.aos.presentation.todolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayout
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentActionlistBinding
import com.growthook.aos.presentation.home.HomeViewModel
import com.growthook.aos.presentation.todolist.completed.CompletedActionlistFragment
import com.growthook.aos.presentation.todolist.proceeding.ProceedingActionlistFragment
import com.growthook.aos.util.base.BaseFragment

class TodolistFragment : BaseFragment<FragmentActionlistBinding>() {
    private val viewModel: HomeViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentActionlistBinding = FragmentActionlistBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNickname()
        observeActionplanPercent()
        initItem()
        clickTabItem()
    }

    private fun observeNickname() {
        viewModel.nickname.observe(viewLifecycleOwner) { nickName ->
            binding.tvActionlistTitle.text = "${nickName}님의 액션"
        }
    }

    private fun observeActionplanPercent() {
        viewModel.actionplanPercent.observe(viewLifecycleOwner) { percent ->
            binding.tvActionlisTPercent.text = "$percent% 달성!"
        }
    }

    private fun initItem() {
        parentFragmentManager.commit {
            replace(R.id.fcv_actionlist_main, ProceedingActionlistFragment(this@TodolistFragment))
        }
    }

    fun moveToCompletedActionTab() {
        val tab: TabLayout.Tab? = binding.tlActionlistMain.getTabAt(1)
        tab?.select()
    }

    private fun clickTabItem() {
        binding.tlActionlistMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment: Fragment = when (tab.position) {
                    0 -> ProceedingActionlistFragment(this@TodolistFragment)
                    1 -> CompletedActionlistFragment()
                    else -> ProceedingActionlistFragment(this@TodolistFragment)
                }
                parentFragmentManager.commit {
                    replace(R.id.fcv_actionlist_main, fragment)
                }
                Log.d("actionlist", "tab.position:: ${tab.position}")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}
