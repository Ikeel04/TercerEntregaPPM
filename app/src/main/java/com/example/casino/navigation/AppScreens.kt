package com.example.casino.navigation

sealed class AppScreens (val route: String) {
    object LoginScreen: AppScreens("login_screen")
    object RegisterScreen: AppScreens("register_screen")
    object LuckyCharmView: AppScreens("lucky_charms_view")

}