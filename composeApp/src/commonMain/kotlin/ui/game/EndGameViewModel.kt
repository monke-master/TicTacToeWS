package ui.game

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.models.GameStatus
import domain.usecase.GetGameSessionUseCase
import domain.usecase.QuitGameUseCase
import domain.usecase.RestartGameUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EndGameViewModel:
    BaseSharedViewModel<EndGameState, EndGameAction, EndGameEvent>(EndGameState.Idle),
    KoinComponent
{
    private val quitGameUseCase: QuitGameUseCase by inject()
    private val restartGameUseCase: RestartGameUseCase by inject()
    private val getGameSessionUseCase: GetGameSessionUseCase by inject()

    override fun obtainEvent(viewEvent: EndGameEvent) {
        when(viewEvent) {
            EndGameEvent.QuitGame -> quitGame()
            EndGameEvent.RestartGame -> restartGame()
        }
    }

    private fun quitGame() {
        viewModelScope.launch {
            quitGameUseCase.execute().onFailure {
                viewAction = EndGameAction.QuitScreenWithError(it)
                return@launch
            }

            viewAction = EndGameAction.QuitScreen
        }
    }

    private fun restartGame() {
        viewModelScope.launch {
            val flow = getGameSessionUseCase.execute().getOrElse {
                viewAction = EndGameAction.QuitScreenWithError(it)
                return@launch
            }
            val session = flow.first() ?: return@launch
            restartGameUseCase.execute(session).onFailure {
                viewAction = EndGameAction.QuitScreenWithError(it)
                return@launch
            }

            viewState = EndGameState.WaitingPlayer

            flow.collect { session ->
                session?.let {
                    if (session.game.gameStatus == GameStatus.Started) {
                        viewAction = EndGameAction.StartGameScreen
                    }
                }
            }
        }
    }
}