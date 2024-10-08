package ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import tictactoe.composeapp.generated.resources.*
import ui.composable.TextButton
import ui.navigation.NavRoute
import ui.theme.Blue
import ui.theme.Green
import ui.theme.LightBlue
import ui.theme.Red

@Composable
fun EndGameDialog(
    viewEndGameStatus: ViewEndGameStatus,
) {
    val rootController = LocalRootController.current

    Dialog(
        onDismissRequest = {}
    ) {
        StoredViewModel(factory = { EndGameViewModel() }) { viewModel ->
            val viewState = viewModel.viewStates().observeAsState()
            val viewAction = viewModel.viewActions().observeAsState()

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
                    when (viewEndGameStatus) {
                        ViewEndGameStatus.Draw -> DrawHeader()
                        ViewEndGameStatus.Win -> WinHeader()
                        ViewEndGameStatus.Defeat -> DefeatHeader()
                    }

                    when (viewState.value) {
                        EndGameState.Idle ->
                            RestartGameButton { viewModel.obtainEvent(EndGameEvent.RestartGame) }
                        EndGameState.WaitingPlayer -> WaitingPlayerHeader()
                    }
                    QuitGameButton { viewModel.obtainEvent(EndGameEvent.QuitGame) }
                }
            }

            viewAction.value?.let {  action ->
                when (action) {
                    EndGameAction.QuitScreen -> rootController.push(NavRoute.StartNavRoute.route)
                    is EndGameAction.QuitScreenWithError -> rootController.push(NavRoute.StartNavRoute.route)
                    EndGameAction.StartGameScreen -> rootController.push(NavRoute.GameNavRoute.route)
                }
            }
        }
    }
}

@Composable
private fun DefeatHeader() {
    Text(
        fontSize = 38.sp,
        text = stringResource(Res.string.defeat),
        color = Red
    )
}

@Composable
private fun WinHeader() {
    Text(
        fontSize = 38.sp,
        text = stringResource(Res.string.victory),
        color = Green
    )
}

@Composable
private fun DrawHeader() {
    Text(
        fontSize = 38.sp,
        text = stringResource(Res.string.draw),
        color = Color.Yellow
    )
}

@Composable
private fun WaitingPlayerHeader() {
    Text(
        fontSize = 24.sp,
        text = stringResource(Res.string.waiting_for_second_player),
    )
}

@Composable
private fun RestartGameButton(
    onClicked: () -> Unit
) {
    TextButton(
        modifier = Modifier.width(225.dp).padding(top = 24.dp),
        text = stringResource(Res.string.restart),
        onClick = onClicked
    )
}

@Composable
private fun QuitGameButton(
    onClicked: () -> Unit
) {
    TextButton(
        modifier = Modifier.width(225.dp).padding(top = 24.dp),
        text = stringResource(Res.string.quit),
        onClick = onClicked
    )
}

@Composable
@Preview
private fun DialogPreview() {
    EndGameDialog(ViewEndGameStatus.Draw)
}