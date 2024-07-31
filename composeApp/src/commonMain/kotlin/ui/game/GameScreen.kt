package ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import domain.models.Cell
import domain.models.CellType
import domain.models.GameSession
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.ic_cross
import tictactoe.composeapp.generated.resources.ic_nought
import tictactoe.composeapp.generated.resources.your_opponent_turn
import tictactoe.composeapp.generated.resources.your_turn
import ui.composable.ErrorPlaceholder
import ui.composable.GradientBackground
import ui.composable.LoadingPlaceholder
import ui.composable.VerticalDivider
import ui.navigation.NavRoute
import ui.theme.Grey

@Composable
fun GameScreen() {
    val rootController = LocalRootController.current

    StoredViewModel({ GameViewModel() }) { viewModel ->
        LaunchedEffect(Unit) {
            viewModel.obtainEvent(GameScreenEvent.LoadGameData)
        }
        GradientBackground {
            val state = viewModel.viewStates().observeAsState()
            val action = viewModel.viewActions().observeAsState()

            when (val value = state.value) {
                is GameScreenState.Error -> ErrorPlaceholder(value.error)
                GameScreenState.Idle -> {}
                GameScreenState.Loading -> LoadingPlaceholder(Modifier.fillMaxSize())
                is GameScreenState.Success ->
                    SuccessState(
                        gameSession = value.gameSession,
                        isPlayerTurn = value.isPlayerTurn,
                        obtainEvent = viewModel::obtainEvent
                    )
            }

            action.value?.let {  value ->
                when (value) {
                    is GameScreenAction.ShowEndGameDialog -> {
                        EndGameDialog(
                            viewEndGameStatus = value.status,
                            obtainEvent = viewModel::obtainEvent
                        )
                    }
                    GameScreenAction.ShowErrorMessage -> {

                    }
                    is GameScreenAction.QuitScreen -> rootController.push(NavRoute.StartNavRoute.route)
                }
            }
        }
    }

}

@Composable
private fun SuccessState(
    gameSession: GameSession,
    isPlayerTurn: Boolean,
    obtainEvent: (GameScreenEvent) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Timer(
            modifier = Modifier.padding(top = 72.dp),
            time = "0:05"
        )
        TurnInfo(
            modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
            turnInfo = stringResource(if (isPlayerTurn) Res.string.your_turn else Res.string.your_opponent_turn)
        )
        GameGrid(
            cells = gameSession.game.field.flatten(),
            columns = gameSession.game.field.size,
            isPlayerTurn = isPlayerTurn,
            obtainEvent = obtainEvent
        )
    }
}

@Composable
private fun Timer(
    time: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(100.dp)
    ) {
        Text(
            text = time,
            fontSize = 34.sp,
            modifier = Modifier.padding(
                horizontal = 32.dp,
                vertical = 8.dp
            )
        )
    }
}

@Composable
private fun TurnInfo(
    modifier: Modifier = Modifier,
    turnInfo: String
) {
    Text(
        text = turnInfo,
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        fontSize = 24.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        maxLines = 1
    )
}

@Composable
private fun GameGrid(
    cells: List<Cell>,
    columns: Int,
    isPlayerTurn: Boolean,
    obtainEvent: (GameScreenEvent) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 28.dp, vertical = 16.dp)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(28.dp).fillMaxWidth(),
            columns = GridCells.Fixed(columns)
        ) {
            items(cells.size) { index ->
                CellItem(
                    cell = cells[index],
                    index = index,
                    onClicked = { index ->
                        if (isPlayerTurn) obtainEvent(GameScreenEvent.OnCellClicked(index))
                    },
                    showHorizontalDivider = index / columns < columns - 1,
                    showVerticalDivider =  (index + 1) % columns != 0,
                )
            }
        }
    }
}

@Composable
private fun CellItem(
    cell: Cell,
    index: Int,
    showVerticalDivider: Boolean,
    showHorizontalDivider: Boolean,
    onClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.clickable { onClicked(index) }
    ) {
        Row(
            modifier = Modifier.height(90.dp)
        ) {
            when (cell.type) {
                CellType.Nought -> CellImage(Res.drawable.ic_nought, modifier.weight(1f))
                CellType.Cross -> CellImage(Res.drawable.ic_cross, modifier.weight(1f))
                null -> Box(modifier = modifier.weight(1f)) {}
            }
            if (showVerticalDivider) {
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = Grey
                )
            }
        }
        if (showHorizontalDivider) {
            Divider(
                color = Grey,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun CellImage(
    resId: DrawableResource,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(resId),
        modifier = modifier,
        contentDescription = null
    )
}

@Composable
@Preview
private fun GameScreenPreview() {
    GameScreen()
}