package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.app_name
import tictactoe.composeapp.generated.resources.host
import tictactoe.composeapp.generated.resources.join
import ui.composable.DefaultBackground
import ui.composable.GradientBackground
import ui.composable.TextButton
import ui.theme.ApplicationTheme

@Composable
fun StartScreen() {
    DefaultBackground {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                fontSize = 42.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 200.dp),
            )
            TextButton(
                text = stringResource(Res.string.join),
                modifier = Modifier.width(225.dp)
            ) {}
            TextButton(
                text = stringResource(Res.string.host),
                modifier = Modifier.width(225.dp).padding(top = 16.dp)
            ) {}
        }
    }
}

@Preview
@Composable
private fun StartScreenPreview() {
    ApplicationTheme {
        StartScreen()
    }
}