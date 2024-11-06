package com.example.casino.navigation

sealed class AppScreens (val route: String) {
    object LoginScreen: AppScreens("login_screen")
    object RegisterScreen: AppScreens("register_screen")
    object LuckyCharmView: AppScreens("lucky_charms_view")
    object LuckySpinScreen: AppScreens("lucky_spin_screen")
    object StatisticsScreen: AppScreens("statistics_screen")
    object Juego: AppScreens("juego_screen")
    object LobbyScreen: AppScreens("lobby_screen")
}