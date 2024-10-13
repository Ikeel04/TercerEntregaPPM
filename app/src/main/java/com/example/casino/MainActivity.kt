package com.example.casino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.casino.navigation.AppNavigation
import com.example.casino.ui.theme.CasinoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CasinoTheme {
        AppNavigation()
    }
}


