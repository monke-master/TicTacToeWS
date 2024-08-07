package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.cancel
import tictactoe.composeapp.generated.resources.ok
import ui.theme.Blue
import ui.theme.LightBlue

@Composable
fun ErrorDialog(
    errorMessage: String,
    onDismissed: () -> Unit
) {
    var isShowed by remember {
        mutableStateOf(true)
    }

    val onDismiss = {
        onDismissed()
        isShowed = false
    }

    if (isShowed) {
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Surface(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(listOf(LightBlue, Blue)),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .fillMaxWidth(),
                color = Color.Unspecified,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(
                        text = errorMessage,
                        fontSize = 24.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    TextButton(
                        modifier = Modifier.width(225.dp).padding(top = 24.dp),
                        text = stringResource(Res.string.ok),
                        onClick = onDismiss
                    )
                }
            }
        }
    }
}


@Composable
fun AlertDialog(
    isShowed: Boolean,
    header: String,
    onDismissed: () -> Unit,
    onYesBtnClicked: () -> Unit
) {

    if (isShowed) {
        Dialog(
            onDismissRequest = onDismissed
        ) {
            Surface(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(listOf(LightBlue, Blue)),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .fillMaxWidth(),
                color = Color.Unspecified,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(
                        text = header,
                        fontSize = 24.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            modifier = Modifier
                                .clickable { onDismissed() },
                            text = stringResource(Res.string.cancel),
                            color = Color.White,
                            fontSize = 20.sp
                        )

                        Text(
                            modifier = Modifier
                                .clickable { onYesBtnClicked() },
                            text = stringResource(Res.string.ok),
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun AlertDialogPreview() {
    AlertDialog(
        isShowed = true,
        header = "Ахмат говно",
        onDismissed = {},
        onYesBtnClicked = {}
    )
}