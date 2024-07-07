package com.growthook.aos.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.growthook.aos.R

@Composable
fun MainScreen() {
    val navigationItems =
        listOf(
            MainNavigationItems.Home,
            MainNavigationItems.Todo,
            MainNavigationItems.MyPage,
        )

    val showTopBar = remember { mutableStateOf(true) }
    val showBottomBar = remember { mutableStateOf(true) }
    val title =
        remember {
            mutableStateOf("홈")
        }
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            if (showBottomBar.value) {
                Column {
                    Divider(color = colorResource(id = R.color.Gray400))
                    GrowthookBottomBar(
                        items = navigationItems,
                        currentDestination = navController.currentBackStackEntryAsState().value?.destination,
                        onNavigateToDestination = {
                            title.value =
                                when (it) {
                                    "홈" -> "HomeScreen"
                                    "할 일" -> "TodoScreen"
                                    else -> "MyPageScreen"
                                }
                            navController.navigate(it) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        },
                    )
                }

            }
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                ThookNavigationGraph(navController = navController)
            }
        },
        containerColor = Color.Transparent,
    )
}
