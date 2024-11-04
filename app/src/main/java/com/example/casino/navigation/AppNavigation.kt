package com.example.casino.navigation

import LuckySpinScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.casino.AuthViewModel
import com.example.casino.screens.LoginScreen
import com.example.casino.screens.LuckyCharmsView
import com.example.casino.screens.RegisterScreen
import com.example.casino.screens.StatisticsScreen

@Composable
fun AppNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route ) {
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController, authViewModel)
        }
        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController, authViewModel)
        }
        composable(route = AppScreens.LuckyCharmView.route){
            LuckyCharmsView(navController, authViewModel)
        }
        composable(route = AppScreens.LuckySpinScreen.route){
            LuckySpinScreen(navController)
        }
        composable(route = AppScreens.StatisticsScreen.route){
            StatisticsScreen(navController)
        }
    }
}

