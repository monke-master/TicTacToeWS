package ui.join

import androidx.compose.foundation.layout.*
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
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import tictactoe.composeapp.generated.resources.*
import ui.composable.ErrorDialog
import ui.composable.DefaultBackground
import ui.composable.ErrorPlaceholder
import ui.composable.LoadingPlaceholder
import ui.composable.TextButton
import ui.navigation.NavRoute
import ui.navigation.registerOnBackCallback

@Composable
fun JoinGameScreen() {
    val rootController = LocalRootController.current

    StoredViewModel(factory = { JoinGameViewModel() }) { viewModel ->

        val state = viewModel.viewStates().observeAsState()
        val action = viewModel.viewActions().observeAsState()

        action.value?.let { value ->
            when (value) {
                JoinGameAction.StartGameAction -> {
                    viewModel.obtainEvent(JoinGameEvent.ActionObtained)
                    rootController.push(NavRoute.GameNavRoute.route)
                }
                JoinGameAction.ExitScreen -> {
                    viewModel.obtainEvent(JoinGameEvent.ActionObtained)
                    rootController.popBackStack()
                }
                is JoinGameAction.ShowErrorDialog -> ErrorDialog(value.message, value.onDismiss)
            }

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
                when (val value = state.value) {
                    is JoinGameState.Error ->
                        ErrorPlaceholder(
                            error = value.error,
                            onTryAgain = { viewModel.obtainEvent(JoinGameEvent.RetryJoin) }
                        )
                    JoinGameState.Idle -> IdleState(obtainEvent = viewModel::obtainEvent)
                    JoinGameState.Loading -> LoadingPlaceholder(Modifier.fillMaxSize())
                    is JoinGameState.Success -> SuccessState(viewModel::obtainEvent)
                }
            }

        }
    }
}

@Composable
private fun IdleState(
    obtainEvent: (JoinGameEvent) -> Unit
) {
    val rootController = LocalRootController.current
    var input by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        ) {
            rootController.push(NavRoute.ScanQrNavRoute.route, params = { code: String ->
                obtainEvent(JoinGameEvent.JoinGame(code))
            })
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            modifier = Modifier
                .width(225.dp)
                .padding(bottom = 16.dp),
            text = stringResource(Res.string.join),
            onClick = {
                obtainEvent(JoinGameEvent.JoinGame(input))
            }
        )
        TextButton(
            modifier = Modifier
                .width(225.dp)
                .padding(bottom = 32.dp),
            text = stringResource(Res.string.quit),
            onClick = {
                rootController.popBackStack()
            }
        )
    }
}

@Composable
private fun SuccessState(
    obtainEvent: (JoinGameEvent) -> Unit
) {
    val rootController = LocalRootController.current

    registerOnBackCallback {
        obtainEvent(JoinGameEvent.QuitGame)
        rootController.popBackStack()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.you_are_connected),
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Text(
            text = stringResource(Res.string.waiting_for_host),
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            modifier = Modifier
                .width(225.dp)
                .padding(bottom = 32.dp),
            text = stringResource(Res.string.quit),
            onClick = {
                obtainEvent(JoinGameEvent.QuitGame)
                rootController.popBackStack()
            }
        )
    }
}

@Composable
@Preview
private fun JoinGameScreenPreview() {
    JoinGameScreen()
}