package com.example.casino.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.casino.AuthState
import com.example.casino.AuthViewModel
import com.example.casino.R
import com.example.casino.navigation.AppScreens

@Composable
fun LuckyCharmsView(navController: NavController, authViewModel: AuthViewModel) {
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate(route = AppScreens.LoginScreen.route)
            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
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

            // Primera fila con botones de las secciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                FeatureSection(
                    title = "LuckySpin",
                    iconRes = R.drawable.luckyspin,
                    onClick = { navController.navigate(AppScreens.LuckySpinScreen.route) } // Navega a LuckySpin
                )
                FeatureSection(
                    title = "Torneo",
                    iconRes = R.drawable.torneo,
                    onClick = { /* Acción o navegación para Torneo */ }
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
                    onClick = { /* Acción o navegación para Ruleta */ }
                )
                FeatureSection(
                    title = "Estadísticas",
                    iconRes = R.drawable.estadisticas,
                    onClick = { /* Acción o navegación para Estadísticas */ }
                )
            }

            // Botón de cerrar sesión
            TextButton(
                onClick = {
                    authViewModel.logout()
                },
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
            .clickable { onClick() } // Acción al hacer clic
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
