package ui.join

import com.adeo.kviewmodel.BaseSharedViewModel
import data.ServerResponse
import domain.usecase.JoinGameUseCase
import domain.models.GameStatus
import domain.usecase.QuitGameUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import ui.host.HostGameAction

class JoinGameViewModel:
    BaseSharedViewModel<JoinGameState, JoinGameAction, JoinGameEvent>(JoinGameState.Idle), KoinComponent {

    private val joinGameUseCase: JoinGameUseCase by inject()
    private val quitGameUseCase: QuitGameUseCase by inject()

    override fun obtainEvent(viewEvent: JoinGameEvent) {
        when(viewEvent) {
            is JoinGameEvent.JoinGame -> joinGame(viewEvent.code)
            JoinGameEvent.ActionObtained -> viewAction = null
        }
    }

    private fun joinGame(code: String) {
        viewModelScope.launch {
            viewState = JoinGameState.Loading

            val flow = joinGameUseCase.execute(code).getOrElse {
                viewState = JoinGameState.Error(it)
                return@launch
            }

            flow.collect { response ->
                if (response == null) return@collect

                when (response) {
                    is ServerResponse.Error -> {
                        viewAction = JoinGameAction.ShowErrorDialog(
                            message = response.errorMessage,
                            onDismiss = {
                                viewAction = JoinGameAction.ExitScreen
                            }
                        )
                        quitGameUseCase.execute()
                    }
                    is ServerResponse.Success -> {
                        viewState = JoinGameState.Success(response.gameSession)
                        if (response.gameSession.game.gameStatus == GameStatus.Started) {
                            viewAction = JoinGameAction.StartGameAction
                        }
                    }
                }
            }
        }
    }

}