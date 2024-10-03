package com.example.casino

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.casino.ui.theme.CasinoTheme

@Composable
fun RegisterScreen(navController: NavHostController) {
    val (username, setUsername) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (confirmPassword, setConfirmPassword) = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Registro", fontSize = 30.sp)

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = username, onValueChange = setUsername, label = { Text("Usuario") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = password, onValueChange = setPassword, label = { Text("Contraseña") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = confirmPassword, onValueChange = setConfirmPassword, label = { Text("Confirmar Contraseña") })

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* Acción de registro */ }) {
                Text(text = "Registrar")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Volver")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegister() {
    CasinoTheme {
        val navController = rememberNavController()
        RegisterScreen(navController = navController)
    }
}
