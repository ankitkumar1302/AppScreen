package com.example.appscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appscreen.navigation.AppNavigation
import com.example.appscreen.navigation.Screen
import com.example.appscreen.presentation.component.AppTopBar
import com.example.appscreen.presentation.component.BottomNavigationBar
import com.example.appscreen.ui.theme.AppScreenTheme
import com.example.appscreen.ui.theme.GradientDarkEnd
import com.example.appscreen.ui.theme.GradientDarkStart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppScreenTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Determine which bottom nav item should be selected
    val selectedItem = when (currentRoute) {
        Screen.Home.route -> 0
        Screen.Stats.route, Screen.Energy.route -> 1
        Screen.Settings.route, Screen.Notifications.route -> 2
        Screen.Profile.route -> 3
        else -> 0
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GradientDarkStart,
                        GradientDarkEnd
                    )
                )
            )
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    onMenuClick = { /* TODO: Implement menu */ },
                    onNotificationClick = { 
                        navController.navigate(Screen.Notifications.route)
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    selectedItem = selectedItem,
                    onItemSelected = { index ->
                        when (index) {
                            0 -> navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                            1 -> navController.navigate(Screen.Stats.route) {
                                popUpTo(Screen.Home.route)
                            }
                            2 -> navController.navigate(Screen.Settings.route) {
                                popUpTo(Screen.Home.route)
                            }
                            3 -> navController.navigate(Screen.Profile.route) {
                                popUpTo(Screen.Home.route)
                            }
                        }
                    }
                )
            },
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AppNavigation(navController)
            }
        }
    }
}
