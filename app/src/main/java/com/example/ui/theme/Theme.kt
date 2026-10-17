package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LordNineColorScheme = darkColorScheme(
    primary = GoldAccent,
    onPrimary = ObsidianBackground,
    primaryContainer = DarkSlateElevated,
    onPrimaryContainer = GoldLight,
    secondary = GoldBorder,
    onSecondary = ObsidianBackground,
    secondaryContainer = DarkSlate,
    onSecondaryContainer = TextPrimary,
    tertiary = GradeEpic,
    onTertiary = Color.White,
    background = ObsidianBackground,
    onBackground = TextPrimary,
    surface = DarkSlate,
    onSurface = TextPrimary,
    surfaceVariant = DarkSlateElevated,
    onSurfaceVariant = TextSecondary,
    outline = GoldBorder,
    outlineVariant = SlateBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false, // MMORPG themed styling
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LordNineColorScheme,
        typography = Typography,
        content = content
    )
}
