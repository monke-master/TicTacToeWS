package domain.usecase

import data.GameRepositoryImpl
import domain.GameRepository
import domain.models.GameSession

class OnOpponentLeftUseCase(
    private val gameRepository: GameRepository
) {

    suspend fun execute(gameSession: GameSession): Result<Unit> {
        val newSession = gameSession.copy(
            players = gameSession.players.subList(0, 1)
        )
        return gameRepository.sendGameSessionData(newSession)
    }

}