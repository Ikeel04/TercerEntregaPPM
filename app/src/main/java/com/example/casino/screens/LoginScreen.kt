package com.example.casino.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.casino.AuthState
import com.example.casino.AuthViewModel
import com.example.casino.navigation.AppScreens

private val primaryColor = Color(0xFFAD0201)
private val linkColor = Color(0xFF2E96E8)
private val largeTextSize = 45.sp
private val smallTextSize = 12.sp
private val buttonTextSize = 20.sp
private val buttonHeight = 60.dp
private val textFieldSpacing = 20.dp

@Composable
fun LoginScreen(navController : NavController, authViewModel: AuthViewModel) {
    val (username, setUsername) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate(route = AppScreens.LuckyCharmView.route)
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LoginText("LUCKY CHARM")
            Spacer(modifier = Modifier.height(50.dp))

            LoginTextField(value = username, label = "E-MAIL", onValueChange = setUsername)
            Spacer(modifier = Modifier.height(textFieldSpacing))

            LoginTextField(value = password, label = "CONTRASEÑA", onValueChange = setPassword)
            Spacer(modifier = Modifier.height(textFieldSpacing))

            LoginButton(onClick = {
                authViewModel.login(username, password)
            })
            Spacer(modifier = Modifier.height(textFieldSpacing))

            LoginTextButton("¿HAS OLVIDADO TU CONTRASEÑA?", onClick = { /* Acción para la contraseña olvidada */ })
            LoginTextButton("¿NO TIENES CUENTA?", onClick = {
                navController.navigate(route = AppScreens.RegisterScreen.route)
            })
        }
    }
}

@Composable
fun LoginText(text: String) {
    Text(
        text = text,
        fontSize = largeTextSize,
        fontWeight = FontWeight.Bold,
        color = primaryColor
    )
}

@Composable
fun LoginTextField(value: String, label: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 13.sp) },
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(buttonHeight)
            .width(200.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryColor,
            contentColor = Color.White
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
    ) {
        Text(text = "INICIAR SESIÓN", fontSize = buttonTextSize)
    }
}

@Composable
fun LoginTextButton(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.height(40.dp)
    ) {
        Text(
            text = text,
            fontSize = smallTextSize,
            fontWeight = FontWeight.Medium,
            textDecoration = TextDecoration.Underline,
            color = linkColor
        )
    }
}

