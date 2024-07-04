package com.growthook.aos.presentation

import com.growthook.aos.R

sealed class GrowthookScreen(val route: String, val title: String, val icon: Int) {
    data object Home : GrowthookScreen("home", "홈", R.drawable.ic_home)
    data object Todo : GrowthookScreen("todoList", "할 일", R.drawable.ic_planlist)
    data object MyPage : GrowthookScreen("mypage", "마이페이지", R.drawable.ic_mypage)
}
