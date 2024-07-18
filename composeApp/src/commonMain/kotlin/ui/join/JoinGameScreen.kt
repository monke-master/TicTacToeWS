package ui.join

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tictactoe.composeapp.generated.resources.*
import ui.composable.DefaultBackground
import ui.composable.TextButton

@Composable
fun JoinGameScreen() {
    var input by remember {
        mutableStateOf("")
    }
    DefaultBackground {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(Res.string.join),
                fontSize = 42.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 42.dp)
            )
            Text(
                text = stringResource(Res.string.enter_code),
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            TextField(
                modifier = Modifier.padding(top = 32.dp).fillMaxWidth(),
                value = input,
                onValueChange = { input = it },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    cursorColor = Color.Black
                )
            )
            TextButton(
                modifier = Modifier
                    .width(225.dp)
                    .padding(top = 32.dp),
                text = stringResource(Res.string.scan_qr)
            ) {      }
            
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                modifier = Modifier
                    .width(225.dp)
                    .padding(bottom = 16.dp),
                text = stringResource(Res.string.join)
            ) {      }
            TextButton(
                modifier = Modifier
                    .width(225.dp)
                    .padding(bottom = 32.dp),
                text = stringResource(Res.string.quit)
            ) {      }
        }
       
    }
}

@Composable
@Preview
private fun JoinGameScreenPreview() {
    JoinGameScreen()
}