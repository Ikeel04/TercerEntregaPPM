package com.example.casino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.casino.navigation.AppNavigation
import com.example.casino.ui.theme.CasinoTheme

class MainActivity : ComponentActivity() {

    private val counterViewModel: CounterViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CasinoTheme {
                AppNavigation(authViewModel = authViewModel, counterViewModel = counterViewModel)
            }
        }
    }
}