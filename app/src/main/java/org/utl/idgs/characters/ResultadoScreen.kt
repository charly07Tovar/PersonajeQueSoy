package org.utl.idgs.characters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen(month: Int) {
    val personaje = when(month) {
        1 -> "Superhéroe de Enero"
        2 -> "Superhéroe de Febrero"
        3 -> "Superhéroe de Marzo"
        4 -> "Superhéroe de Abril"
        5 -> "Superhéroe de Mayo"
        6 -> "Superhéroe de Junio"
        7 -> "Superhéroe de Julio"
        8 -> "Superhéroe de Agosto"
        9 -> "Superhéroe de Septiembre"
        10 -> "Superhéroe de Octubre"
        11 -> "Superhéroe de Noviembre"
        12 -> "Superhéroe de Diciembre"
        else -> "Superhéroe Genérico"
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Tu personaje es: $personaje", fontSize = 24.sp)
    }
}