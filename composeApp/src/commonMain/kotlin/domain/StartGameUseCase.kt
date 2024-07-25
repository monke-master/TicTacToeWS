package domain

import domain.models.GameSession
import domain.models.GameStatus

class StartGameUseCase(
    private val gameRepository: GameRepository
) {

    suspend fun execute(gameSession: GameSession): Result<Unit> {
        val session = gameSession.copy(game = gameSession.game.copy(gameStatus = GameStatus.Started))
        return gameRepository.sendGameSessionData(session)
    }
}