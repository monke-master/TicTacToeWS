package ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
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
import tictactoe.composeapp.generated.resources.error
import tictactoe.composeapp.generated.resources.try_again
import ui.theme.Red

@Composable
fun LoadingPlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ErrorPlaceholder(
    error: Throwable,
    onTryAgain: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.error),
            fontSize = 36.sp,
            color = Red,
            textAlign = TextAlign.Center
        )
        error.message?.let {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = it,
                fontSize = 28.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        TextButton(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(Res.string.try_again),
            onClick = onTryAgain
        )
    }
}


@Composable
@Preview
private fun ErrorPlaceholderPreview() {
    GradientBackground(
        
    ) {
        ErrorPlaceholder(object: Throwable() {
            override val message: String?
                get() = "Хохлы пидорасы"
        }, {})
    }
}