package com.example.casino

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        // Pantalla principal
        composable("main") {
            LuckyCharmsView(navController)
        }
        // Pantalla de inicio de sesión
        composable("login") {
            LoginScreen(navController)
        }
        // Pantalla de registro
        composable("register") {
            RegisterScreen(navController)
        }
    }
}

