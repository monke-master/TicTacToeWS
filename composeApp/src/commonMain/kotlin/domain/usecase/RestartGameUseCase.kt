package domain.usecase

import domain.GameRepository
import domain.models.GameSession

class RestartGameUseCase(
    private val gameRepository: GameRepository
) {

    suspend fun execute(gameSession: GameSession) = gameRepository.restartGame(gameSession.code)

}