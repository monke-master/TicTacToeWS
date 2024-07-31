package ui.join

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.usecase.JoinGameUseCase
import domain.models.GameStatus
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class JoinGameViewModel:
    BaseSharedViewModel<JoinGameState, JoinGameAction, JoinGameEvent>(JoinGameState.Idle), KoinComponent {

    private val joinGameUseCase: JoinGameUseCase by inject()

    override fun obtainEvent(viewEvent: JoinGameEvent) {
        when(viewEvent) {
            is JoinGameEvent.JoinGame -> joinGame(viewEvent.code)
        }
    }

    private fun joinGame(code: String) {
        viewModelScope.launch {
            viewState = JoinGameState.Loading

            val flow = joinGameUseCase.execute(code).getOrElse {
                viewState = JoinGameState.Error(it)
                return@launch
            }

            flow.collect { session ->
                session?.let {
                    viewState = JoinGameState.Success(it)
                    if (it.game.gameStatus == GameStatus.Started) {
                        viewAction = JoinGameAction.StartGameAction
                    }
                }
            }
        }
    }

}