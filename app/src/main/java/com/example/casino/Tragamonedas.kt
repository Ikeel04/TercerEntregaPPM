package com.example.menu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.menu.ui.theme.MenuTheme

class Tragamonedas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuTheme {
                LuckySpinScreen()
            }
        }
    }
}
//Body del tragamonedas
@Composable
fun LuckySpinScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Image(painter = painterResource(id = R.drawable.fondo1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize())
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Header()
            IconCards()
            WinMessage()
            ActionButtons()
            SpinButton()
        }
    }
}
//fuente definida para el título del juego
val makmure = FontFamily(Font(R.font.makmure))
//Encabezado de la app que contiene el título y la corona
@Composable
fun Header() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 30.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.corona),
            contentDescription = "Corona",
            modifier = Modifier.size(90.dp)
                .padding(top = 35.dp)
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color(0xFFFFD700), shape = RoundedCornerShape(30.dp)) // Bordes redondeados para la sección dorada
            .padding(10.dp) // Margen interno para la sección dorada
            .width(220.dp)
    ) {
        Text(
            text = "LuckySpin",
            color = Color(0xFF7D2596),
            fontSize = 50.sp,
            fontFamily = makmure,
        )
    }
}
//Cards con cada slot del tragamonedas
@Composable
fun IconCards() {
    Box(
        modifier = Modifier
            .width(350.dp)
            .padding(start = 16.dp)
            .padding(end = 16.dp)
            .background(Color(0xFFFFD700), shape = RoundedCornerShape(16.dp)) // Bordes redondeados para el contenedor
            .padding(15.dp) // Margen interno
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()

        ) {
            CardItem(R.drawable.corazon)
            CardItem(R.drawable.arcoiris)
            CardItem(R.drawable.herradura)
        }
    }
}
//Especificaciones para las cards
@Composable
fun CardItem(iconRes: Int) {
    Box(
        modifier = Modifier
            .size(100.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.size(100.dp)
        ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                    .border(7.dp, Color(0xFFFFD700))
                )
        }
    }
}
//Mensaje de ganador o perdedor
@Composable
fun WinMessage() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(Color(0xFFFFD700), shape = RoundedCornerShape(40.dp)) // Bordes redondeados para la sección dorada
                .padding(10.dp) // Margen interno para la sección dorada
                .width(180.dp)
        ) {
            Text(
                text = "¡You win!",
                fontSize = 30.sp,
                color = Color(0xFF7D2596),
                fontFamily = makmure,
            )
        }
    }

//Box para el saldo disponible y un textField para que el usuario ingrese cuánto saldo desea apostar
@Composable
fun ActionButtons() {
    var apuesta by remember { mutableStateOf("") }
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
                Text(
                    text = "Saldo disponible: 10,000",
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = apuesta,
                onValueChange = { apuesta = it },
                label = { Text("Saldo a apostar") }
            )
        }
    }
}

//Botón de girar
@Composable
fun SpinButton() {
    Column(
        modifier = Modifier.size(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { /* Acción Girar */ },
            modifier = Modifier
                .padding(bottom = 120.dp)
                .width(190.dp)
                .height(60.dp)
                .fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF13d110),
                    contentColor = Color.White
        )


            ) {
            Text(text = "¡Girar!", fontSize = 24.sp)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewLuckySpinScreen() {
    MenuTheme {
        LuckySpinScreen()
    }
}