package com.example.team_koeln_bonn.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


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