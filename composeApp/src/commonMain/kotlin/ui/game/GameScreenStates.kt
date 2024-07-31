package ui.game

import domain.models.GameSession

sealed class GameScreenEvent {

    data object LoadGameData: GameScreenEvent()
    data class OnCellClicked(val index: Int): GameScreenEvent()
    data object QuitGame: GameScreenEvent()
}

sealed class GameScreenAction {

    data class ShowEndGameDialog(
        val status: ViewEndGameStatus
    ): GameScreenAction()

    data object ShowErrorMessage: GameScreenAction()

    data object QuitScreen: GameScreenAction()
}

sealed class GameScreenState {

    data object Idle: GameScreenState()

    data object Loading: GameScreenState()

    data class Error(
        val error: Throwable
    ): GameScreenState()

    data class Success(
        val gameSession: GameSession,
        val isPlayerTurn: Boolean
    ): GameScreenState()

}