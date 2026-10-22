package com.bloom.wellness.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = BloomPrimary,
    onPrimary = BloomOnPrimary,
    primaryContainer = BloomPrimaryContainer,
    onPrimaryContainer = BloomOnPrimaryContainer,
    secondary = BloomSecondary,
    onSecondary = BloomOnSecondary,
    secondaryContainer = BloomSecondaryContainer,
    onSecondaryContainer = BloomOnSecondaryContainer,
    tertiary = BloomTertiary,
    onTertiary = Color.White,
    tertiaryContainer = BloomTertiaryContainer,
    onTertiaryContainer = BloomOnTertiaryContainer,
    background = BloomBackground,
    onBackground = BloomOnBackground,
    surface = BloomSurface,
    onSurface = BloomOnSurface,
    surfaceVariant = BloomSurfaceVariant,
    onSurfaceVariant = BloomOnSurfaceVariant,
    outline = BloomOutline,
    outlineVariant = BloomOutlineVariant
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB1C8),
    onPrimary = Color(0xFF5E112A),
    primaryContainer = Color(0xFF7B2940),
    onPrimaryContainer = Color(0xFFFFD9E2),
    secondary = Color(0xFFB0D5D8),
    onSecondary = Color(0xFF1E383A),
    secondaryContainer = Color(0xFF334E51),
    onSecondaryContainer = Color(0xFFCCE8EA),
    background = Color(0xFF1B1517),
    onBackground = Color(0xFFEBE0E3),
    surface = Color(0xFF221A1D),
    onSurface = Color(0xFFEBE0E3),
    surfaceVariant = Color(0xFF352B30),
    onSurfaceVariant = Color(0xFFD6C3CA)
)

@Composable
fun BloomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
