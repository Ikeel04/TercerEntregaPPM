package com.example.casino.navigation

sealed class AppScreens (val route: String) {
    object LoginScreen: AppScreens("login_screen")
    object RegisterScreen: AppScreens("register_screen")
    object LuckyCharmView: AppScreens("lucky_charms_view")
    object LuckySpinScreen: AppScreens("lucky_spin_screen")
    object RouletteScreen: AppScreens("ruleta_screen")
    object StatisticsScreen: AppScreens("statistics_screen")
    object PiedraPapelTijerasApp: AppScreens("piedra_papel_tijeras_app")
    object LobbyScreen: AppScreens("lobby_screen")
}