package com.example.appscreen.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import com.example.appscreen.ui.theme.DarkBlue
import com.example.appscreen.ui.theme.DeepGrey
import com.example.appscreen.ui.theme.SubtleCyan
import com.example.appscreen.ui.theme.NeonAccent
import com.example.appscreen.ui.theme.GreyishTone
import com.example.appscreen.ui.theme.White

private val DarkColorScheme = darkColorScheme(
    primary = SubtleCyan,
    secondary = DeepGrey,
    tertiary = NeonAccent,
    background = DarkBlue,
    surface = DeepGrey,
    onPrimary = DarkBlue,
    onSecondary = White,
    onTertiary = DarkBlue,
    onBackground = White,
    onSurface = White,
    error = NeonAccent,
    onError = DarkBlue
)

@Composable
fun AppScreenTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
