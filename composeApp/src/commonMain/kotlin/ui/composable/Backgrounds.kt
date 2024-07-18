package ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.back_cross
import tictactoe.composeapp.generated.resources.back_nought
import ui.theme.Blue
import ui.theme.LightBlue

@Composable
fun GradientBackground(
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .background(Brush.verticalGradient(listOf(LightBlue, Blue)))
            .fillMaxSize(),
        content = content,
        color = Color.Unspecified
    )
}

@Composable
fun DefaultBackground(
    content: @Composable () -> Unit
) {
    GradientBackground {
        Box {
            Image(
                painter = painterResource(Res.drawable.back_cross),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopStart).offset(x = (-70).dp, y = 100.dp)
            )
            Image(
                painter = painterResource(Res.drawable.back_nought),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopEnd).offset(x = 150.dp)
            )
            content()
        }
    }
}

@Composable
@Preview
private fun DefaultBackgroundPreview() {
    DefaultBackground {
        
    }
}