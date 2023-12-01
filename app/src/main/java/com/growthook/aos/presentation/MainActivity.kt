package com.growthook.aos.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.growthook.aos.R
import com.growthook.aos.databinding.ActivityMainBinding
import com.growthook.aos.presentation.home.HomeFragment
import com.growthook.aos.presentation.mypage.MyPageFragment
import com.growthook.aos.presentation.planlist.PlanlistFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.bnvHome.selectedItemId = R.id.menu_home
        supportFragmentManager.findFragmentById(R.id.fcv_home)
            ?: navigateTo<HomeFragment>()
        binding.bnvHome.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> navigateTo<HomeFragment>()
                R.id.menu_planlist -> navigateTo<PlanlistFragment>()
                R.id.menu_mypage -> navigateTo<MyPageFragment>()
            }
            true
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_home, T::class.simpleName)
        }
    }

    fun hideBottomNavigation(state: Boolean) {
        if (state) {
            binding.bnvHome.visibility = View.GONE
        } else {
            binding.bnvHome.visibility =
                View.VISIBLE
        }
    }
}
