package ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = AppTypography(),
        content = content
    )
}