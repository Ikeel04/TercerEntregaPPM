package com.example.casino.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.casino.AuthViewModel
import com.example.casino.CounterViewModel
import com.example.casino.screens.LoginScreen
import com.example.casino.screens.LuckyCharmsView
import com.example.casino.screens.RegisterScreen
import com.example.casino.screens.StatisticsScreen
import com.example.casino.screens.LobbyScreen
import com.example.casino.screens.LuckySpinScreen
import com.example.casino.screens.RouletteScreen

@Composable
fun AppNavigation(authViewModel: AuthViewModel, counterViewModel: CounterViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route ) {
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController, authViewModel)
        }
        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController, authViewModel)
        }
        composable(route = AppScreens.LuckyCharmView.route){
            LuckyCharmsView(navController, authViewModel, counterViewModel) // Pasa counterViewModel aquí
        }
        composable(route = AppScreens.LuckySpinScreen.route){
            LuckySpinScreen(navController, counterViewModel)
        }
        composable(route = AppScreens.RouletteScreen.route){
            RouletteScreen(navController)
        }
        composable(route = AppScreens.StatisticsScreen.route){
            StatisticsScreen(navController, counterViewModel) // Pasa counterViewModel aquí
        }
        composable(route = AppScreens.LobbyScreen.route){
            LobbyScreen(navController)
        }
    }
}