package com.example.casino.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.casino.CounterViewModel
import com.example.casino.R
import com.example.casino.ui.theme.CasinoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CasinoTheme {
                val navController = rememberNavController()
                PiedraPapelTijerasApp()
            }
        }
    }
}

@Composable
fun PiedraPapelTijerasApp(
    counterViewModel: CounterViewModel = viewModel()
) {
    val saldo by counterViewModel.saldo.collectAsState()
    var currentPlayer by remember { mutableStateOf(1) }
    var player1Choice by remember { mutableStateOf<Int?>(null) }
    var player2Choice by remember { mutableStateOf<Int?>(null) }
    var resultMessage by remember { mutableStateOf("") }
    var apuesta by remember { mutableStateOf("") }

    val options = listOf(
        Pair(R.drawable.tijerasjuego, "Tijeras"),
        Pair(R.drawable.piedrajuego, "Piedra"),
        Pair(R.drawable.papeljuegos, "Papel")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF004D40))
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sección del Jugador actual
        PlayerSection(
            title = "Jugador $currentPlayer",
            choices = options.map { it.first },
            selectedChoice = if (currentPlayer == 1) player1Choice else player2Choice,
            onChoiceClick = { index ->
                if (currentPlayer == 1) {
                    player1Choice = index
                    currentPlayer = 2
                } else {
                    player2Choice = index
                    resultMessage = determineWinner(player1Choice!!, player2Choice!!)
                    currentPlayer = 1

                    val apuestaValue = apuesta.toIntOrNull() ?: 0
                    when (resultMessage) {
                        "¡Jugador 1 gana!" -> counterViewModel.agregarGanancia(apuestaValue)
                        "¡Jugador 2 gana!" -> counterViewModel.agregarPerdida(apuestaValue)
                    }
                }
            }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 100.dp)
        ) {
            if (player1Choice != null && player2Choice != null) {
                Text(
                    text = "Jugador 1: ${options[player1Choice!!].second} vs Jugador 2: ${options[player2Choice!!].second}",
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = resultMessage,
                    fontSize = 24.sp,
                    color = when (resultMessage) {
                        "¡Jugador 1 gana!" -> Color.Green
                        "¡Jugador 2 gana!" -> Color.Red
                        else -> Color.Yellow
                    },
                    textAlign = TextAlign.Center
                )
            }
        }

        ApuestaButtons(
            saldo = saldo,
            apuesta = apuesta,
            onApuestaChange = { apuesta = it }
        )
    }
}

@Composable
fun PlayerSection(
    title: String,
    choices: List<Int>,
    selectedChoice: Int?,
    onChoiceClick: ((Int) -> Unit)? = null
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            fontSize = 25.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 100.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, bottom = 50.dp)
        ) {
            choices.forEachIndexed { index, imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(
                            if (selectedChoice == index) Color.Yellow else Color.Transparent
                        )
                        .clickable(enabled = onChoiceClick != null) {
                            onChoiceClick?.invoke(index)
                        }
                )
            }
        }
    }
}

@Composable
fun ApuestaButtons(saldo: Int, apuesta: String, onApuestaChange: (String) -> Unit) {
    Box(
        modifier = Modifier.padding(50.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(10.dp)
                    .width(260.dp)
            ) {
                Text(text = "Saldo disponible: $saldo")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = apuesta,
                onValueChange = onApuestaChange,
                label = { Text("Saldo a apostar") },
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
            )
        }
    }
}

fun determineWinner(player1Choice: Int, player2Choice: Int): String {
    return when {
        player1Choice == player2Choice -> "Empate"
        (player1Choice == 0 && player2Choice == 2) || // Tijeras gana a Papel
                (player1Choice == 1 && player2Choice == 0) || // Piedra gana a Tijeras
                (player1Choice == 2 && player2Choice == 1) -> // Papel gana a Piedra
            "¡Jugador 1 gana!"
        else -> "¡Jugador 2 gana!"
    }
}

@Preview(showBackground = true)
@Composable
fun PiedraPapelTijerasPreview() {
    CasinoTheme {
        rememberNavController()
        PiedraPapelTijerasApp()
    }
}