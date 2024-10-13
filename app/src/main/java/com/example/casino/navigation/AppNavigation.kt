package com.example.casino.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.casino.screens.LoginScreen
import com.example.casino.screens.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route ) {
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }
    }
}

