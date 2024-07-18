package ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.montserrat

@Composable
fun AppTypography() = Typography(
    defaultFontFamily = FontFamily(
       Font(Res.font.montserrat)
    )
)