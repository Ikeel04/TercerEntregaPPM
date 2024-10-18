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
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

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
            IconCards()
            WinMessage(result)
            ActionButtons()
            SpinButton(onSpinClick = {
                coroutineScope.launch {
                    val randomNumber = if (context.isOnline()) {
                        getRandomNumberFromApi()
                    } else {
                        getRandomNumberLocal()
                    }
                    result = if (randomNumber == 3) {
                        "¡You win!"
                    } else {
                        "You lose"
                    }
                }
            })
            Button(
                onClick = {navController.popBackStack()},
                modifier = Modifier
                    .padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF13d110),
                    contentColor = Color.White
                )
            ) {Text(text = "Volver") }
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
fun IconCards() {
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
            CardItem(R.drawable.corazon)
            CardItem(R.drawable.arcoiris)
            CardItem(R.drawable.herradura)
        }
    }
}

@Composable
fun CardItem(iconRes: Int) {
    Box(
        modifier = Modifier.size(100.dp)
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
                    .background(Color(0xFFFFD700))
            )
        }
    }
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
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)}


fun getRandomNumberLocal(): Int {
    return Random.nextInt(1, 6)
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

@Preview (showBackground = true)
@Composable
fun LuckySpinScreenPreview() {
    CasinoTheme {
        val navController = rememberNavController()
        LuckySpinScreen(navController)
    }
}
