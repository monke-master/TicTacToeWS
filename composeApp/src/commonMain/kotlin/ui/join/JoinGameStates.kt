package ui.join

import domain.models.GameSession

sealed class JoinGameState {

    data object Idle: JoinGameState()

    data object Loading: JoinGameState()

    data class Error(
        val error: Throwable
    ): JoinGameState()

    data class Success(
        val session: GameSession
    ): JoinGameState()
}

sealed class JoinGameAction {

    data object StartGameAction: JoinGameAction()

    data class ShowErrorDialog(
        val message: String,
        val onDismiss: () -> Unit
    ): JoinGameAction()

    data object ExitScreen: JoinGameAction()
}

sealed class JoinGameEvent {

    data class JoinGame(val code: String): JoinGameEvent()
}