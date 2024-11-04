package com.example.casino.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.casino.R

@Composable
fun StatisticsScreen(navController : NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),// Agrega padding superior para separar el contenido del borde
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .offset(y = 100.dp), // Desplaza la columna hacia abajo si necesitas más separación
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "TUS ESTADÍSTICAS",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Statistic items
            StatisticItem(label = "GANANCIAS:", value = "Q 0,000,000.00")
            Spacer(modifier = Modifier.height(16.dp))
            StatisticItem(label = "PARTIDAS JUGADAS:", value = "0,000 PJ")
            Spacer(modifier = Modifier.height(16.dp))
            StatisticItem(label = "RACHA DE VICTORIAS:", value = "00 SEGUIDAS")
            Spacer(modifier = Modifier.height(16.dp))
            StatisticItem(label = "JUEGO FAVORITO:", value = "NOMBRE JUEGO")

            Spacer(modifier = Modifier.height(40.dp))

            // Close Button
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(
                    text = "CERRAR",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun StatisticItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = value,
            fontSize = 18.sp,
            color = Color(0xFFFFA000),
            textAlign = TextAlign.End
        )
    }
}
