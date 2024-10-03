package com.example.casino

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TournamentTree() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF174941)), // Fondo verde
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TORNEO TORNADO\nLUCKY CHARMS",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        // Cuarta Fila (4 jugadores)
        Row(modifier = Modifier.padding(vertical = 16.dp )) {
            PlayerCircle("jugador")
            Spacer(modifier = Modifier.width(16.dp))
            PlayerCircle("jugador")
            Spacer(modifier = Modifier.width(16.dp))
            PlayerCircle("jugador")
            Spacer(modifier = Modifier.width(16.dp))
            PlayerCircle("jugador")
        }
        // Primera Fila (2 jugadores)
        Row(modifier = Modifier.padding(vertical = 16.dp)) {
            PlayerCircle("jugador")
            Spacer(modifier = Modifier.width(64.dp))
            PlayerCircle("jugador")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Segunda Fila (1 jugador)
        PlayerCircle("jugador")

        Spacer(modifier = Modifier.height(24.dp))

        PlayerCircle("jugador")

        Spacer(modifier = Modifier.height(10.dp))

        // Tercera Fila (2 jugadores)
        Row(modifier = Modifier.padding(vertical = 16.dp)) {
            PlayerCircle("jugador")
            Spacer(modifier = Modifier.width(64.dp))
            PlayerCircle("jugador")
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Cuarta Fila (4 jugadores)
        Row(modifier = Modifier.padding(vertical = 16.dp )) {
            PlayerCircle("jugador")
            Spacer(modifier = Modifier.width(16.dp))
            PlayerCircle("jugador")
            Spacer(modifier = Modifier.width(16.dp))
            PlayerCircle("jugador")
            Spacer(modifier = Modifier.width(16.dp))
            PlayerCircle("jugador")
        }

        Spacer(modifier = Modifier.height(44.dp))

        // Apuesta
        Text(
            text = "Apuestas de $10",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun PlayerCircle(name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .border(2.dp, Color.White, CircleShape)
                .background(Color.LightGray, CircleShape)
        ) {

        }
        Text(text = name, fontSize = 16.sp, color = Color.White)
    }
}

@Preview
@Composable
fun PreviewTournamentTree() {
    TournamentTree()
}