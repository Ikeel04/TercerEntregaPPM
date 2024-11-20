package com.example.casino.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.casino.R
import com.example.casino.ui.theme.CasinoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Ruleta : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CasinoTheme {
                val navController = rememberNavController()
                RouletteScreen(navController)
            }
        }
    }
}

@Composable
fun RouletteScreen(navController: NavController) {
    var saldo by remember { mutableStateOf(1000) }
    var apuesta by remember { mutableStateOf("") }
    var isSpinning by remember { mutableStateOf(false) }
    var resultadoRuleta by remember { mutableStateOf<Int?>(null) }
    var apuestaSeleccionada by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "RULETA PIRULETA",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )

            RouletteWheel(isSpinning)

            Spacer(modifier = Modifier.height(5.dp))

            ActionButtonsRuleta(saldo, apuesta) { apuesta = it }

            Spacer(modifier = Modifier.height(5.dp))

            RouletteBoard()

            Spacer(modifier = Modifier.height(2.dp))

            BettingButtons(
                saldo = saldo,
                apuesta = apuesta,
                onSeleccionarApuesta = { seleccion -> apuestaSeleccionada = seleccion }
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Muestra el resultado de la ruleta
            resultadoRuleta?.let { resultado ->
                Text(
                    text = "Resultado: $resultado",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
            //La ruleta gira al presionar el botón "Girar" y también está el botón para volver
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        val apuestaValor = apuesta.toIntOrNull()
                        if (apuestaValor != null && apuestaValor <= saldo && apuestaSeleccionada != null) {
                            isSpinning = true
                            coroutineScope.launch {
                                delay(3000) // Duración de la animación
                                val resultado = (0..36).random() // Genera el número ganador
                                resultadoRuleta = resultado
                                isSpinning = false
                                evaluarResultado(
                                    resultado,
                                    saldo,
                                    apuestaValor,
                                    apuestaSeleccionada!!,
                                    { nuevoSaldo -> saldo = nuevoSaldo }
                                )
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF13d110),
                        contentColor = Color.White
                    )
                ) {
                    Text("¡Girar!")
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) { Text(text = "Back") }
            }
        }
    }
}
//Imagen de la ruleta
@Composable
fun RouletteWheel(isSpinning: Boolean) {
    Box(
        modifier = Modifier
            .size(300.dp)
            .animateRotation(isSpinning) // Aplicamos la animación
            .clip(CircleShape)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ruleta),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}
//Campos de saldo disponible y saldo a apostar
@Composable
fun ActionButtonsRuleta(saldo: Int, apuesta: String, onApuestaChange: (String) -> Unit) {
    Box(
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
                label = { Text("Saldo a apostar") }
            )
        }
    }
}
//Tablero de la ruleta
@Composable
fun RouletteBoard() {
    val numbers = listOf(
        0 to Color.Green,
        32 to Color.Red, 15 to Color.Black, 19 to Color.Red, 4 to Color.Black, 21 to Color.Red,
        2 to Color.Black, 25 to Color.Red, 17 to Color.Black, 34 to Color.Red, 6 to Color.Black,
        27 to Color.Red, 13 to Color.Black, 36 to Color.Red, 11 to Color.Black, 30 to Color.Red,
        8 to Color.Black, 23 to Color.Red, 10 to Color.Black, 5 to Color.Red, 24 to Color.Black,
        16 to Color.Red, 33 to Color.Black, 1 to Color.Red, 20 to Color.Black, 14 to Color.Red,
        31 to Color.Black, 9 to Color.Red, 22 to Color.Black, 18 to Color.Red, 29 to Color.Black,
        7 to Color.Red, 28 to Color.Black, 12 to Color.Red, 35 to Color.Black, 3 to Color.Red, 26 to Color.Black
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(11),
        content = {
            items(numbers) { (number, color) ->
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .background(color)
                        .size(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = number.toString(), color = Color.White)
                }
            }
        }
    )
}
// Botones de apuestas
@Composable
fun BettingButtons(
    saldo: Int,
    apuesta: String,
    onSeleccionarApuesta: (String) -> Unit
) {
    val options = listOf(
        "RED", "BLACK", "EVEN", "ODD", "1 to 12",
        "13 to 24", "25 to 36", "1 to 18", "19 to 36"
    )
    Column(
        modifier = Modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        options.chunked(3).forEach { rowOptions ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowOptions.forEach { option ->
                    Button(
                        onClick = { onSeleccionarApuesta(option) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (option == "RED") Color.Red else if (option == "BLACK") Color.Black else Color.DarkGray
                        )
                    ) {
                        Text(option, color = Color.White)
                    }
                }
            }
        }
    }
}
//Función para saber si el jugador ganó o perdió dependiendo de la apuesta realizada, se actualiza su saldo
fun evaluarResultado(
    resultado: Int,
    saldo: Int,
    apuestaValor: Int,
    tipoApuesta: String,
    actualizarSaldo: (Int) -> Unit
) {
    val numbers = mapOf(
        "RED" to listOf(32, 19, 21, 25, 34, 27, 36, 30, 23, 5, 16, 1, 14, 9, 18, 7, 12, 3),
        "BLACK" to listOf(15, 4, 2, 17, 6, 13, 11, 8, 10, 24, 33, 20, 31, 22, 29, 28, 35, 26),
        "EVEN" to (2..36 step 2).toList(),
        "ODD" to (1..35 step 2).toList(),
        "1 to 12" to (1..12).toList(),
        "13 to 24" to (13..24).toList(),
        "25 to 36" to (25..36).toList(),
        "1 to 18" to (1..18).toList(),
        "19 to 36" to (19..36).toList()
    )

    val esGanador = numbers[tipoApuesta]?.contains(resultado) == true
    val premio = when (tipoApuesta) {
        "RED", "BLACK", "EVEN", "ODD", "1 to 18", "19 to 36" -> apuestaValor * 2
        "1 to 12", "13 to 24", "25 to 36" -> apuestaValor * 3
        else -> 0
    }

    actualizarSaldo(if (esGanador) saldo + premio else saldo - apuestaValor)
}
//Giro de la ruleta
@Composable
fun Modifier.animateRotation(isSpinning: Boolean): Modifier {
    val rotation by animateFloatAsState(
        targetValue = if (isSpinning) 3600f else 0f, // 10 giros completos
        animationSpec = tween(durationMillis = 3000) // Duración de la animación
    )
    return this.graphicsLayer(rotationZ = rotation)
}


@Preview(showBackground = true)
@Composable
fun RuletaPreview() {
    CasinoTheme {
        val navController = rememberNavController()
        RouletteScreen(navController)
    }
}
