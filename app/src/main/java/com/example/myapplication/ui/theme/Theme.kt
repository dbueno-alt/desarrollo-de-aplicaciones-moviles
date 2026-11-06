package com.example.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Aplicamos los colores de Master Martini al tema claro
private val LightColorScheme = lightColorScheme(
    primary = VerdeMartini,
    secondary = DoradoMartini,
    tertiary = RojoMartini,
    background = Blanco,
    surface = Blanco,
    onPrimary = Blanco,
    onSecondary = NegroMartini,
    onBackground = NegroMartini,
    onSurface = NegroMartini
)

@Composable
fun MasterMartiniTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography, // Asegúrate de tener este archivo en la misma carpeta
        content = content
    )
}