package com.example.casino.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Bottom
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

    Box(
        modifier = Modifier
            .fillMaxSize()
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
                // Espacio superior
                Spacer(modifier = Modifier.height(25.dp))

                // com.example.menu.Ruleta
                RouletteWheel()

                // Opciones de apuestas
                ActionButtonsRuleta(saldo, apuesta) {
                    apuesta = it
                }

                //Tablero de numeros
                RouletteBoard()

                //botones de acción
                BettingButtons()
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(top = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF13d110),
                        contentColor = Color.White
                    )
                ) { Text(text = "Volver") }
            }
        }
    }

@Composable
fun RouletteWheel() {

    Text(
        text = "RULETA PIRULETA",
        fontSize = 35.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
    )
    Spacer(modifier = Modifier.height(15.dp))
    Box(
        modifier = Modifier
            .size(300.dp) // Tamaño de la ruleta
            .clip(CircleShape)
    ) {
        // Flecha en el centro de la ruleta
        Image(
            painter = painterResource(id = R.drawable.ruleta), // Reemplazar con la imagen de la flecha
            contentDescription = null,
            modifier = Modifier
                .size(560.dp)
                .align(Alignment.Center)
        )
    }
}
@Composable
fun ActionButtonsRuleta(saldo: Int, apuesta: String, onApuestaChange: (String) -> Unit) {
    Box(
        modifier = Modifier.padding(20.dp)
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
@Composable
fun RouletteBoard() {
    val numbers = listOf(
        32 to Color.Red, 15 to Color.Black, 19 to Color.Red, 4 to Color.Black, 21 to Color.Red, 2 to Color.Black, 25 to Color.Red, 17 to Color.Black, 34 to Color.Red, 6 to Color.Black, 27 to Color.Red, 13 to Color.Black, 36 to Color.Red, 11 to Color.Black, 30 to Color.Red, 8 to Color.Black, 23 to Color.Red, 10 to Color.Black, 5 to Color.Red, 24 to Color.Black, 16 to Color.Red, 33 to Color.Black, 1 to Color.Red, 20 to Color.Black, 14 to Color.Red, 31 to Color.Black, 9 to Color.Red, 22 to Color.Black, 18 to Color.Red, 29 to Color.Black, 7 to Color.Red, 28 to Color.Black, 12 to Color.Red, 35 to Color.Black, 36 to Color.Red, 3 to Color.Red, 26 to Color.Black, 0 to Color.Green
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
@Composable
fun BettingButtons() {
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                Text("1 to 12")
            }
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                Text("13 to 24")
            }
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                Text("25 to 36")
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                Text("1 to 18")
            }
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                Text("Even")
            }
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                Text("Odd")
            }
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                Text("19 to 36")
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text("RED")
            }
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                Text("BLACK")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RuletaPreview() {
    CasinoTheme {
        val navController = rememberNavController()
        RouletteScreen(navController)
    }
}
