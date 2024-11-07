package com.example.casino.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.casino.AuthState
import com.example.casino.AuthViewModel
import com.example.casino.CounterViewModel
import com.example.casino.R
import com.example.casino.navigation.AppScreens
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.casino.ui.theme.CasinoTheme


@Composable
fun LuckyCharmsView(
    navController: NavController,
    authViewModel: AuthViewModel,
    counterViewModel: CounterViewModel = viewModel()
) {
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate(route = AppScreens.LoginScreen.route)
            else -> Unit
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "LUCKY CHARMS",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                FeatureSection(
                    title = "LuckySpin",
                    iconRes = R.drawable.luckyspin,
                    onClick = {
                        counterViewModel.incrementLuckySpinCount()
                        navController.navigate(AppScreens.LuckySpinScreen.route)
                    }
                )
                FeatureSection(
                    title = "Torneo",
                    iconRes = R.drawable.torneo,
                    onClick = {
                        counterViewModel.incrementTorneoCount()
                        navController.navigate(AppScreens.LobbyScreen.route)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Segunda fila con botones de las secciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                FeatureSection(
                    title = "Ruleta",
                    iconRes = R.drawable.ruleta,
                    onClick = {
                        counterViewModel.incrementRuletaCount()
                    }
                )
                FeatureSection(
                    title = "Estadísticas",
                    iconRes = R.drawable.estadisticas,
                    onClick = { navController.navigate(AppScreens.StatisticsScreen.route) }
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Botón de cerrar sesión
            Button(
                onClick = {
                    authViewModel.logout()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Cerrar Sesión",
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
fun FeatureSection(title: String, iconRes: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            modifier = Modifier
                .size(160.dp)
                .padding(16.dp)
        )
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        }
    }
@Preview(showBackground = true)
@Composable
fun LuckyCharmsViewPreview() {
    CasinoTheme {
        val navController = rememberNavController()
        val authViewModel = AuthViewModel()
        val counterViewModel = CounterViewModel()
        LuckyCharmsView(
            navController = navController,
            authViewModel = authViewModel,
            counterViewModel = counterViewModel
        )
    }
}