package com.growthook.aos.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.growthook.aos.App
import com.growthook.aos.R
import com.growthook.aos.presentation.MainActivityViewModel
import com.growthook.aos.presentation.home.HomeScreen
import com.growthook.aos.presentation.mypage.MyPageScreen
import com.growthook.aos.presentation.onboarding.OnboardingActivity
import com.growthook.aos.presentation.todolist.TodoScreen
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainComposeActivity : ComponentActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
            isAlreadyLogin()
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()

        if (USER_ID != 0) {
            App.trackEvent("${viewModel.memberId.value} + 나가기")
            Timber.d("amplitude 나가기 leave")
        }
    }

    override fun onDestroy() {
        if (USER_ID != 0) {
            App.trackEvent("${viewModel.memberId.value} + 나가기")
            Timber.d("amplitude 나가기 destroy")
        }
        super.onDestroy()
    }

    private fun isAlreadyLogin() {
        viewModel.isAlreadyLogin.observe(this) { isAlreadyLogin ->
            if (!isAlreadyLogin) {
                val intent =
                    Intent(this, OnboardingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

            USER_ID = viewModel.memberId.value ?: 0

            if (USER_ID != 0) {
                App.trackEvent("$USER_ID + 앱 실행")
            }
        }
    }

    companion object {
        var USER_ID = 0
    }
}

@Composable
fun ThookNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainNavigationItems.Home.route,
    ) {
        // NavGraphBuilder 구현
        composable(MainNavigationItems.Home.route) {
            HomeScreen()
        }
        composable(MainNavigationItems.Todo.route) {
            TodoScreen()
        }
        composable(MainNavigationItems.MyPage.route) {
            MyPageScreen()
        }
    }
}

@Composable
fun GrowthookBottomBar(
    items: List<MainNavigationItems>,
    currentDestination: NavDestination?,
    onNavigateToDestination: (route: String) -> Unit,
) {
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.Gray700),
        contentColor = colorResource(id = R.color.Gray700),
        modifier =
            Modifier
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom),
                ).height(76.dp),
    ) {
        items.forEach { item ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == item.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(item.route) },
                icon = {
                    val icon =
                        if (selected) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        }
                    Icon(
                        imageVector = ImageVector.vectorResource(icon),
                        modifier = Modifier.size(28.dp),
                        contentDescription = null,
                    )
                },
                label = {
                    val labelColor =
                        if (selected) {
                            colorResource(id = R.color.MainGreen400)
                        } else {
                            colorResource(id = R.color.Gray400)
                        }
                    Text(text = item.title, color = labelColor)
                },
                colors =
                    NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor =
                            colorResource(
                                id = R.color.MainGreen400,
                            ),
                    ),
            )
        }
    }
}

@Preview
@Composable
fun MainActivityPreview() {
    MainComposeActivity()
}
