package com.example.team_koeln_bonn.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


//Basically hier wurden die Farben von Color.kt genommen und es praktisch einzusetzen
private val LightColorScheme = lightColorScheme(
    primary = AppOrange,
    secondary = AppBlue,
    tertiary = AppPurple,

    background = AppBackground,
    surface = AppBackground,

    onPrimary = ButtonContent,
    onSecondary = ButtonContent,
    onTertiary = ButtonContent,

    onBackground = AppText,
    onSurface = AppText
)

@Composable // Wir benutzen nur Lightmode
fun Team_Koeln_BonnTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}