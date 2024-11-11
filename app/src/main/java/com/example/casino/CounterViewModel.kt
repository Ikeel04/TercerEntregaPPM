package com.example.casino

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel() {

    // Contadores existentes
    private val _luckySpinCount = MutableStateFlow(0)
    val luckySpinCount: StateFlow<Int> = _luckySpinCount

    private val _torneoCount = MutableStateFlow(0)
    val torneoCount: StateFlow<Int> = _torneoCount

    private val _ruletaCount = MutableStateFlow(0)
    val ruletaCount: StateFlow<Int> = _ruletaCount

    // Nuevo: Dinero ganado y perdido compartido
    private val _dineroGanado = MutableStateFlow(0)
    val dineroGanado: StateFlow<Int> = _dineroGanado

    private val _dineroPerdido = MutableStateFlow(0)
    val dineroPerdido: StateFlow<Int> = _dineroPerdido

    private val _saldo = MutableStateFlow(10000) // Saldo inicial
    val saldo: StateFlow<Int> = _saldo

    // Juego favorito basado en los contadores
    val juegoFavorito: StateFlow<String> = combine(
        luckySpinCount, torneoCount, ruletaCount
    ) { luckySpin, torneo, ruleta ->
        when {
            torneo > luckySpin && torneo > ruleta -> "Torneo"
            luckySpin > torneo && luckySpin > ruleta -> "LuckySpin"
            ruleta > luckySpin && ruleta > torneo -> "Ruleta"
            else -> "NULL"
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "Ruleta"
    )

    // Métodos para incrementar los contadores
    fun incrementLuckySpinCount() {
        _luckySpinCount.value += 1
    }

    fun incrementTorneoCount() {
        _torneoCount.value += 1
    }

    fun incrementRuletaCount() {
        _ruletaCount.value += 1
    }

    // Métodos para actualizar el dinero ganado, perdido y el saldo
    fun agregarGanancia(cantidad: Int) {
        if (cantidad > 0) {
            _dineroGanado.value += cantidad
            _saldo.value += cantidad
        }
    }

    fun agregarPerdida(cantidad: Int) {
        if (cantidad > 0) {
            _dineroPerdido.value += cantidad
            _saldo.value -= cantidad
        }
    }
}