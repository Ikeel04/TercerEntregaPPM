package com.example.casino

data class Usuario(
    val email: String = "",
    val dineroGanado: Double = 0.0,
    val dineroPerdido: Double = 0.0,
    val juegoMasJugado: String = "",
    val victoriasDerrotas: Map<String, Int> = mapOf("victorias" to 0, "derrotas" to 0)
)
