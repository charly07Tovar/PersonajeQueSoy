package org.utl.idgs.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * Este es el composable principal que controla qué pantalla mostrar.
 * Utiliza un estado 'isSearching' para decidir si muestra la pantalla
 * principal o la pantalla de carga.
 */

@Composable
fun CharacterDiscoveryApp() {
    // El estado que controla la visibilidad de la pantalla de carga.
    var isSearching by remember { mutableStateOf(false) }

    // El Scaffold proporciona una estructura básica de Material Design.
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        // Si 'isSearching' es verdadero, muestra la pantalla de carga.
        if (isSearching) {
            LoadingScreen()

            // LaunchedEffect se usa para simular una tarea de fondo (ej. una llamada a API).
            // Se ejecuta solo una vez cuando 'isSearching' se vuelve verdadero.
            LaunchedEffect(Unit) {
                delay(3500L) // Simula una espera de 3.5 segundos.
                isSearching = false // Al terminar, regresa a la pantalla principal.
                // En una app real, aquí navegarías a la pantalla de resultados.
            }
        } else {
            // Si 'isSearching' es falso, muestra la pantalla principal.
            MainScreen(
                modifier = Modifier.padding(paddingValues),
                onDiscoverClick = { isSearching = true } // Al hacer clic, se activa la búsqueda.
            )
        }
    }
}

/**
 * La pantalla principal que contiene el botón para iniciar la búsqueda.
 * @param onDiscoverClick Es una función lambda que se ejecutará cuando se presione el botón.
 */
@Composable
fun MainScreen(modifier: Modifier = Modifier, onDiscoverClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Encuentra tu Personaje",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onDiscoverClick, // Llama a la función pasada como parámetro.
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Descubrir Personaje", fontSize = 16.sp, color = Color.White)
        }
    }
}

/**
 * La pantalla de carga que se muestra durante la simulación.
 * Muestra un indicador de progreso circular y un texto.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(60.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Buscando personaje...",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

// --- Vistas Previa ---

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    // Usamos el tema de Material para la vista previa.
    MaterialTheme {
        MainScreen(onDiscoverClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    MaterialTheme {
        LoadingScreen()
    }
}
