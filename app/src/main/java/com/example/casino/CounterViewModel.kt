package com.example.casino

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel() {
    private val _luckySpinCount = MutableStateFlow(0)
    val luckySpinCount: StateFlow<Int> = _luckySpinCount

    private val _torneoCount = MutableStateFlow(0)
    val torneoCount: StateFlow<Int> = _torneoCount

    private val _ruletaCount = MutableStateFlow(0)
    val ruletaCount: StateFlow<Int> = _ruletaCount

    // Juego favorito calculado en tiempo real
    val juegoFavorito: StateFlow<String> = combine(
        _luckySpinCount, _torneoCount, _ruletaCount
    ) { luckySpin, torneo, ruleta ->
        when {
            torneo > luckySpin && torneo > ruleta -> "Torneo"
            luckySpin > torneo && luckySpin > ruleta -> "LuckySpin"
            ruleta > luckySpin && ruleta > torneo -> "Ruleta"
            else -> "NULL"
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = "Ruleta"
    )

    fun incrementLuckySpinCount() {
        viewModelScope.launch {
            _luckySpinCount.value += 1
        }
    }

    fun incrementTorneoCount() {
        viewModelScope.launch {
            _torneoCount.value += 1
        }
    }

    fun incrementRuletaCount() {
        viewModelScope.launch {
            _ruletaCount.value += 1
        }
    }
}