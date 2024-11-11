package com.example.casino.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun RegisterScreen(navController: NavController, authViewModel: AuthViewModel) {
    val (username, setUsername) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (confirmPassword, setConfirmPassword) = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var passwordsMatch by remember { mutableStateOf(true) }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current
    var checkedState by remember { mutableStateOf(false) }

    LaunchedEffect(authState.value) {
        when(authState.value) {
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
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            RegisterText("LUCKY CHARM")
            Spacer(modifier = Modifier.height(50.dp))

            RegisterTextField(value = username, label = "E-MAIL", onValueChange = setUsername)
            Spacer(modifier = Modifier.height(textFieldSpacing))

            PasswordField(
                value = password,
                label = "CONTRASEÑA",
                onValueChange = {
                    setPassword(it)
                    passwordsMatch = it == confirmPassword
                },
                passwordVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible }
            )
            Spacer(modifier = Modifier.height(textFieldSpacing))

            PasswordField(
                value = confirmPassword,
                label = "CONFIRMAR CONTRASEÑA",
                onValueChange = {
                    setConfirmPassword(it)
                    passwordsMatch = it == password
                },
                passwordVisible = confirmPasswordVisible,
                onVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible }
            )

            if (!passwordsMatch) {
                Text("Las contraseñas no coinciden", color = Color.Red, fontSize = smallTextSize)
            }

            Spacer(modifier = Modifier.height(textFieldSpacing))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checkedState,
                    colors = CheckboxDefaults.colors(
                        checkedColor = primaryColor
                    ),
                    onCheckedChange = { checkedState = it }
                )
                Text("ACEPTO LOS TERMINOS Y CONDICIONES", fontSize = smallTextSize)
            }

            Spacer(modifier = Modifier.height(textFieldSpacing))

            RegisterButton(
                onClick = {
                    if (passwordsMatch) {
                        authViewModel.registro(username, password)
                    } else {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = checkedState
            )

            Spacer(modifier = Modifier.height(textFieldSpacing))

            RegisterTextButton("¿YA TIENES CUENTA?", onClick = {
                navController.popBackStack()
            })
        }
    }
}

@Composable
fun RegisterText(text: String) {
    Text(
        text = text,
        fontSize = largeTextSize,
        fontWeight = FontWeight.Bold,
        color = primaryColor
    )
}

@Composable
fun RegisterTextField(value: String, label: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        label = { Text(label, fontSize = 13.sp) },
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@Composable
fun PasswordField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    onVisibilityChange: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        label = { Text(label, fontSize = 13.sp) },
        modifier = Modifier.fillMaxWidth(0.8f),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onVisibilityChange) {
                Text(if (passwordVisible) "Ocultar" else "Mostrar", color = linkColor, fontSize = smallTextSize)
            }
        }
    )
}

@Composable
fun RegisterButton(onClick: () -> Unit, enabled: Boolean) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .height(buttonHeight)
            .width(200.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) primaryColor else Color.Gray,
            contentColor = Color.White
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
    ) {
        Text(text = "REGISTRARSE", fontSize = buttonTextSize)
    }
}

@Composable
fun RegisterTextButton(text: String, onClick: () -> Unit) {
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