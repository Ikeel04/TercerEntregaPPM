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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {
    val (username, setUsername) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Inicio de Sesión", fontSize = 30.sp)

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = username, onValueChange = setUsername, label = { Text("Usuario") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = password, onValueChange = setPassword, label = { Text("Contraseña") })

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* Acción de inicio de sesión */ }) {
                Text(text = "Entrar")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Volver")
            }
        }
    }
}
