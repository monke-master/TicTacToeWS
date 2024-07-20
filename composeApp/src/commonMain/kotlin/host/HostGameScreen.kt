package host

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import tictactoe.composeapp.generated.resources.*
import ui.composable.DefaultBackground
import ui.composable.TextButton
import ui.theme.Green

@Composable
fun HostGameScreen() {
    val rootController = LocalRootController.current
    DefaultBackground {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.host),
                fontSize = 42.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 42.dp)
            )
            Text(
                text = stringResource(Res.string.tell_the_code),
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Code(
                code = "GOIDA2",
                modifier = Modifier.padding(32.dp)
            )
            QrCode()
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                modifier = Modifier
                                .width(225.dp)
                                .padding(bottom = 32.dp),
                text = stringResource(Res.string.quit),
                onClick = {
                    rootController.popBackStack()
                })
        }
    }
}

@Composable
private fun Code(
    modifier: Modifier = Modifier,
    code: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Green,
        modifier = modifier
    ) {
       Text(
           text = code,
           color = Color.White,
           fontSize = 34.sp,
           modifier = Modifier.padding(horizontal = 48.dp, vertical = 12.dp)
       )
    }
}

@Composable
private fun QrCode() {
     Text(
            text = stringResource(Res.string.scan_qr),
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Box(
            modifier = Modifier
                        .size(150.dp)
                        .background(Color.White)
        ) {}
}

@Preview
@Composable
private fun HostGameScreenPreview() {
    HostGameScreen()
}