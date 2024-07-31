package ui.game

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.CheckPlayerTurnUseCase
import domain.GetCurrentPlayerUseCase
import domain.GetGameSessionUseCase
import domain.MakeTurnUseCase
import domain.models.GameStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

class GameViewModel:
    BaseSharedViewModel<GameScreenState, GameScreenAction, GameScreenEvent>(GameScreenState.Idle),
    KoinComponent {

    private val makeTurnUseCase: MakeTurnUseCase by inject()
    private val getGameSessionUseCase: GetGameSessionUseCase by inject()
    private val checkPlayerTurnUseCase: CheckPlayerTurnUseCase by inject()
    private val getCurrentPlayerUseCase: GetCurrentPlayerUseCase by inject()


    override fun obtainEvent(viewEvent: GameScreenEvent) {
        when (viewEvent) {
            is GameScreenEvent.LoadGameData -> loadData()
            is GameScreenEvent.OnCellClicked -> onCellCLicked(viewEvent.index)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            viewState = GameScreenState.Loading

            val flow = getGameSessionUseCase.execute().getOrElse { error ->
                viewState = GameScreenState.Error(error)
                return@launch
            }

            flow.collect { session ->
                session?.let { session ->
                    viewState = GameScreenState.Success(
                        gameSession = session,
                        isPlayerTurn = checkPlayerTurnUseCase.execute(session)
                    )

                    if (session.game.gameStatus is GameStatus.End) {
                        viewAction = GameScreenAction.ShowEndGameDialog(
                            session.game.gameStatus.endStatus.toViewStatus(getCurrentPlayerUseCase.execute().type))
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

}