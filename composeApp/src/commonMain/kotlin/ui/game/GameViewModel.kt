package ui.game

import com.adeo.kviewmodel.BaseSharedViewModel
import data.ServerResponse
import domain.usecase.CheckPlayerTurnUseCase
import domain.usecase.GetCurrentPlayerUseCase
import domain.usecase.GetGameSessionUseCase
import domain.usecase.MakeTurnUseCase
import domain.models.GameStatus
import domain.usecase.QuitGameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GameViewModel:
    BaseSharedViewModel<GameScreenState, GameScreenAction, GameScreenEvent>(GameScreenState.Idle),
    KoinComponent {

    private val makeTurnUseCase: MakeTurnUseCase by inject()
    private val getGameSessionUseCase: GetGameSessionUseCase by inject()
    private val checkPlayerTurnUseCase: CheckPlayerTurnUseCase by inject()
    private val getCurrentPlayerUseCase: GetCurrentPlayerUseCase by inject()
    private val quitGameUseCase: QuitGameUseCase by inject()


    override fun obtainEvent(viewEvent: GameScreenEvent) {
        when (viewEvent) {
            is GameScreenEvent.LoadGameData -> loadData()
            is GameScreenEvent.OnCellClicked -> onCellCLicked(viewEvent.index)
            is GameScreenEvent.QuitGame -> quitGame()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            viewState = GameScreenState.Loading

            val flow = getGameSessionUseCase.execute().getOrElse { error ->
                viewState = GameScreenState.Error(error)
                return@launch
            }

            flow.collect { response ->
                if (response == null) return@collect

                when (response) {
                    is ServerResponse.Error -> {}
                    is ServerResponse.Success -> {
                        viewState = GameScreenState.Success(
                            gameSession = response.gameSession,
                            isPlayerTurn = checkPlayerTurnUseCase.execute(response.gameSession)
                        )

                        if (response.gameSession.game.gameStatus is GameStatus.End) {
                            viewAction = GameScreenAction.ShowEndGameDialog(
                                response.gameSession.game.gameStatus.endStatus.toViewStatus(getCurrentPlayerUseCase.execute().type))
                        }
                    }
                }
            }
        }
    }

    private fun onCellCLicked(cellIndex: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val gameSession = (viewState as? GameScreenState.Success)?.gameSession ?: return@withContext
                val field = gameSession.game.field
                val cell = field[cellIndex / field.size][cellIndex % field.size]

                if (cell.type != null) {
                    viewAction = GameScreenAction.ShowErrorMessage
                    return@withContext
                }

                makeTurnUseCase.execute(gameSession, cellIndex).getOrElse { error ->
                    viewState = GameScreenState.Error(error)
                }
            }
        }
    }

    private fun quitGame() {
        viewModelScope.launch {
            quitGameUseCase.execute()
            viewAction = GameScreenAction.QuitScreen
        }
    }
}