package ui.host

import domain.models.GameSession

sealed class HostGameAction {

    data object StartGameScreen: HostGameAction()

    data class ShowErrorDialog(
        val message: String,
        val onDismissed: () -> Unit
    ): HostGameAction()

    data object ExitScreen: HostGameAction()
}

sealed class HostGameEvent {

    data object CreateGame: HostGameEvent()

    data object StartGame: HostGameEvent()

    data object QuitGame: HostGameEvent()

    data object RetryRequest: HostGameEvent()

}

sealed class HostGameState {
    data object Loading: HostGameState()

    data object Idle: HostGameState()

    data class Error(
        val error: Throwable
    ): HostGameState()

    data class Success(
        val session: GameSession,
        val qrCode: ByteArray
    ): HostGameState() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as Success

            if (session != other.session) return false
            if (!qrCode.contentEquals(other.qrCode)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = session.hashCode()
            result = 31 * result + qrCode.contentHashCode()
            return result
        }
    }

}
