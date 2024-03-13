package com.growthook.aos.presentation.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentTodolistBinding
import com.growthook.aos.presentation.home.HomeViewModel
import com.growthook.aos.presentation.todolist.completed.CompletedActionlistFragment
import com.growthook.aos.presentation.todolist.proceeding.ProceedingActionlistFragment
import com.growthook.aos.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodolistFragment : BaseFragment<FragmentTodolistBinding>() {
    private val viewModel: HomeViewModel by activityViewModels()
    private val todolistViewModel by viewModels<TodolistViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentTodolistBinding = FragmentTodolistBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        initItem()
        clickTabItem()
    }

    private fun subscribe() {
        observeNickname()
        observeActionplanPercent()
    }

    private fun observeNickname() {
        viewModel.nickname.observe(viewLifecycleOwner) { nickName ->
            binding.tvTodolistTitle.text = "${nickName}님의 할 일"
        }
    }

    private fun observeActionplanPercent() {
        todolistViewModel.actionplanPercent.observe(viewLifecycleOwner) { percent ->
            binding.tvTodolistPercent.text = "$percent% 달성!"
            binding.pgbTodolistMain.progress = percent
        }
    }

    private fun initItem() {
        parentFragmentManager.commit {
            replace(R.id.fcv_actionlist_main, ProceedingActionlistFragment(this@TodolistFragment))
        }
    }

    fun moveToCompletedActionTab() {
        val tab: TabLayout.Tab? = binding.tlTodolistMain.getTabAt(1)
        tab?.select()
    }

    private fun clickTabItem() {
        binding.tlTodolistMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment: Fragment = when (tab.position) {
                    0 -> ProceedingActionlistFragment(this@TodolistFragment)
                    1 -> CompletedActionlistFragment()
                    else -> ProceedingActionlistFragment(this@TodolistFragment)
                }
                parentFragmentManager.commit {
                    replace(R.id.fcv_actionlist_main, fragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}
