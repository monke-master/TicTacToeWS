package ui.host


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import domain.models.GameSession
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import tictactoe.composeapp.generated.resources.*
import ui.ErrorDialog
import ui.composable.DefaultBackground
import ui.composable.LoadingPlaceholder
import ui.composable.TextButton
import ui.navigation.NavRoute
import ui.theme.Green
import utils.toBitmap

@Composable
fun HostGameScreen() {
    val rootController = LocalRootController.current

    StoredViewModel(factory = { HostGameViewModel() }) { viewModel ->
        LaunchedEffect(Unit) {
            viewModel.obtainEvent(HostGameEvent.CreateGame)
        }

        DefaultBackground {
            val state = viewModel.viewStates().observeAsState()
            val action = viewModel.viewActions().observeAsState()

            action.value?.let { value ->
                when (value) {
                    HostGameAction.StartGameScreen -> rootController.push(NavRoute.GameNavRoute.route)
                    is HostGameAction.ShowErrorDialog -> ErrorDialog(value.message, value.onDismissed)
                    HostGameAction.ExitScreen -> rootController.popBackStack()
                }
            }

            when(val value = state.value) {
                is HostGameState.Error -> ErrorState(value.error)
                HostGameState.Idle -> {}
                HostGameState.Loading -> { LoadingPlaceholder(Modifier.fillMaxSize()) }
                is HostGameState.Success -> SuccessState(
                    gameSession = value.session,
                    qrCode = value.qrCode,
                    obtainEvent = viewModel::obtainEvent
                )
            }
        }
    }

}

@Composable
private fun ErrorState(error: Throwable) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = error.message ?: " ")
    }
}

@Composable
private fun SuccessState(
    gameSession: GameSession,
    qrCode: ByteArray,
    obtainEvent: (HostGameEvent) -> Unit
) {
    val rootController = LocalRootController.current
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
        if (gameSession.players.size > 1) {
            StartGameBlock(obtainEvent)
        } else {
            CodeInformation(gameSession, qrCode)
        }
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

@Composable
private fun StartGameBlock(
    obtainEvent: (HostGameEvent) -> Unit
) {
    Text(
        text = stringResource(Res.string.player_connected),
        fontSize = 24.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )
    TextButton(
        modifier = Modifier
            .width(225.dp)
            .padding(top = 32.dp),
        text = stringResource(Res.string.start),
        backgroundColor = Green,
        textColor = Color.White,
        onClick = {
            obtainEvent(HostGameEvent.StartGame)
        })
}


@Composable
private fun CodeInformation(
    gameSession: GameSession,
    qrCode: ByteArray
) {
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
        code = gameSession.code,
        modifier = Modifier.padding(32.dp)
    )
    QrCode(qrCode)
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
private fun QrCode(
    bytes: ByteArray
) {
    Text(
        text = stringResource(Res.string.scan_qr),
        color = Color.White,
        fontSize = 24.sp,
        modifier = Modifier.padding(bottom = 12.dp)
    )
    Image(
        bitmap = bytes.toBitmap(),
        contentDescription = null,
        modifier = Modifier.size(150.dp)
    )
}

@Preview
@Composable
private fun HostGameScreenPreview() {
    HostGameScreen()
}
