package com.example.casino.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.casino.R
import com.example.casino.ui.theme.CasinoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CasinoTheme {
                GameScreen()
            }
        }
    }
}

@Composable
fun GameScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF004D40)), // Color de fondo
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Jugador 1 con sus imágenes y círculo de usuario
        PlayerImagesWithUser(playerName = "Jugador 1", imageIds = listOf(
            R.drawable.tijerasjuego, // Reemplaza con tus imágenes
            R.drawable.papeljuegos,
            R.drawable.piedrajuego
        ))

        // Jugador 2 con sus imágenes y círculo de usuario
        PlayerImagesWithUser(playerName = "Jugador 2", imageIds = listOf(
            R.drawable.tijerasjuego, // Reemplaza con tus imágenes
            R.drawable.papeljuegos,
            R.drawable.piedrajuego
        ))
    }
}

@Composable
fun PlayerImagesWithUser(playerName: String, imageIds: List<Int>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        // Nombre del jugador
        Text(
            text = playerName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Círculo extra para representar al usuario
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Reemplaza con tu imagen de círculo gris
            contentDescription = "Usuario",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray) // Color gris para el círculo del usuario
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Fila de imágenes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            imageIds.forEach { imageId ->
                Image(
                    painter = painterResource(id = imageId), // Reemplaza con las imágenes correctas
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CasinoTheme {
        GameScreen()
    }
}
