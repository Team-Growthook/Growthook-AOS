package com.growthook.aos.presentation.main

import com.growthook.aos.R

sealed class MainNavigationItems(
    val route: String,
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
) {
    data object Home : MainNavigationItems(
        "home",
        "홈",
        selectedIcon = R.drawable.ic_home,
        unselectedIcon = R.drawable.ic_home_unselected,
    )

    data object Todo : MainNavigationItems(
        "todoList",
        "할 일",
        selectedIcon = R.drawable.ic_planlist_selected,
        unselectedIcon = R.drawable.ic_planlist,
    )

    data object MyPage : MainNavigationItems(
        "mypage",
        "마이페이지",
        selectedIcon = R.drawable.ic_mypage,
        unselectedIcon = R.drawable.ic_mypage_unselected,
    )
}
