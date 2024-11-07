package com.example.casino.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.casino.R
import com.example.casino.ui.theme.CasinoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.Composable
import kotlin.random.Random
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

class Tragamonedas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CasinoTheme {
                val navController = rememberNavController()
                LuckySpinScreen(navController)
            }
        }
    }
}

@Composable
fun LuckySpinScreen(navController: NavController) {
    var result by remember { mutableStateOf("") }
    var saldo by remember { mutableStateOf(1000) }
    var apuesta by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Estado para controlar el giro
    var isSpinning by remember { mutableStateOf(false) }
    var reelImages by remember { mutableStateOf(listOf(R.drawable.corazon, R.drawable.arcoiris, R.drawable.herradura)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Header()
            IconCards(reelImages, isSpinning)
            WinMessage(result)
            ActionButtons(saldo, apuesta) {
                apuesta = it
            }
            SpinButton(onSpinClick = {
                val apuestaInt = apuesta.toIntOrNull() ?: 0
                if (apuestaInt > 0 && apuestaInt <= saldo) {
                    coroutineScope.launch {
                        if (context.isOnline()) {
                            getRandomNumberFromApi()
                        } else {
                            getRandomIcon()
                        }
                        isSpinning = true
                        delay(2000L) // Simula el giro de los carretes por 2 segundos
                        isSpinning = false

                        // Genera un número aleatorio para cada carrete al detenerse
                        reelImages = List(3) { getRandomIcon() }
                        if (reelImages.distinct().size == 1) {
                            result = "¡You win!"
                            saldo += apuestaInt
                        } else {
                            result = "You lose"
                            saldo -= apuestaInt
                        }
                        apuesta = "" // Limpiar el campo de apuesta después de girar
                    }
                }
            })
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

val makmure = FontFamily(Font(R.font.makmure))

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
            .background(Color(0xFFFFD700), shape = RoundedCornerShape(30.dp))
            .padding(10.dp)
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

@Composable
fun IconCards(reelImages: List<Int>, isSpinning: Boolean) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .padding(start = 16.dp)
            .padding(end = 16.dp)
            .background(Color(0xFFFFD700), shape = RoundedCornerShape(16.dp))
            .padding(15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            reelImages.forEach { iconRes ->
                CardItem(iconRes, isSpinning)
            }
        }
    }
}

@Composable
fun CardItem(iconRes: Int, isSpinning: Boolean) {
    var currentIcon by remember { mutableStateOf(iconRes) }

    // Si el carrete está girando, cambia la imagen rápidamente
    LaunchedEffect(isSpinning) {
        if (isSpinning) {
            while (isSpinning) {
                currentIcon = getRandomIcon()
                delay(100L) // Velocidad de cambio de imagen
            }
        } else {
            // Al detenerse, mantiene el icono final asignado desde IconCards
            currentIcon = iconRes
        }
    }

    Box(
        modifier = Modifier.size(100.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.size(100.dp)
        ) {
            Image(
                painter = painterResource(id = currentIcon),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
                    .background(Color(0xFFFFD700))
            )
        }
    }
}

fun getRandomIcon(): Int {
    val icons = listOf(R.drawable.corazon, R.drawable.arcoiris, R.drawable.herradura)
    return icons[Random.nextInt(icons.size)]
}
fun getRandomNumberLocal(): Int {
    return Random.nextInt(1, 3)
}

@Composable
fun WinMessage(result: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color(0xFFFFD700), shape = RoundedCornerShape(40.dp))
            .padding(10.dp)
            .width(180.dp)
    ) {
        Text(
            text = result,
            fontSize = 30.sp,
            color = Color(0xFF7D2596),
            fontFamily = makmure,
        )
    }
}

@Composable
fun ActionButtons(saldo: Int, apuesta: String, onApuestaChange: (String) -> Unit) {
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
fun SpinButton(onSpinClick: () -> Unit) {
    Column(
        modifier = Modifier.size(250.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onSpinClick() },
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

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

suspend fun getRandomNumberFromApi(): Int {
    return withContext(Dispatchers.IO) {
        val url = URL("https://csrng.net/csrng/csrng.php?min=1&max=6")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        try {
            connection.inputStream.bufferedReader().use { reader ->
                val response = reader.readText()
                val randomNumber = response.substringAfter("\"random\":").substringBefore("}").toInt()
                randomNumber
            }
        } finally {
            connection.disconnect()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LuckySpinScreenPreview() {
    CasinoTheme {
        val navController = rememberNavController()
        LuckySpinScreen(navController)
    }
}
