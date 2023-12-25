package com.growthook.aos.presentation.actionlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayout
import com.growthook.aos.R
import com.growthook.aos.databinding.FragmentActionlistBinding
import com.growthook.aos.presentation.actionlist.completed.CompletedActionlistFragment
import com.growthook.aos.presentation.actionlist.proceeding.ProceedingActionlistFragment
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
        initItem()
        clickTabItem()
    }

    private fun setTitleText() {
        viewModel.nickname.observe(viewLifecycleOwner) { nickName ->
            binding.tvActionlistTitle.text = "${nickName}님의 액션"
        }
    }

    private fun initItem() {
        parentFragmentManager.commit {
            replace(R.id.fcv_actionlist_main, ProceedingActionlistFragment())
        }
    }

    private fun clickTabItem() {
        binding.tlActionlistMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment: Fragment = when (tab.position) {
                    0 -> ProceedingActionlistFragment()
                    1 -> CompletedActionlistFragment()
                    else -> ProceedingActionlistFragment()
                }
                parentFragmentManager.commit {
                    replace(R.id.fcv_actionlist_main, fragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // not use
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // not use
            }
        })
    }
}
