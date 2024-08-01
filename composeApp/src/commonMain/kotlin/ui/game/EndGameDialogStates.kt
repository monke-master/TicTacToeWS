package ui.game

sealed class EndGameState {

    data object Idle: EndGameState()

    data object WaitingPlayer: EndGameState()

}

sealed class EndGameAction {

    data object QuitScreen: EndGameAction()

    data class QuitScreenWithError(
        val error: Throwable
    ): EndGameAction()

    data object StartGameScreen: EndGameAction()
}

sealed class EndGameEvent {

    data object QuitGame: EndGameEvent()

    data object RestartGame: EndGameEvent()
}