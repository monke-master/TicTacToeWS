package ui.host

import domain.models.GameSession

sealed class HostGameAction {

    data object StartGameScreen: HostGameAction()

}

sealed class HostGameEvent {

    data object CreateGame: HostGameEvent()

    data object StartGame: HostGameEvent()

}

sealed class HostGameState {
    data object Loading: HostGameState()

    data object Idle: HostGameState()

    data class Error(
        val error: Throwable
    ): HostGameState()

    data class Success(
        val session: GameSession
    ): HostGameState()

}
