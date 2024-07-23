package domain

import domain.models.GameSession

class StartGameUseCase(
    private val gameRepository: GameRepository
) {

    suspend fun execute(gameSession: GameSession): Result<Unit> {
        return gameRepository.sendGameSessionData(gameSession)
    }
}