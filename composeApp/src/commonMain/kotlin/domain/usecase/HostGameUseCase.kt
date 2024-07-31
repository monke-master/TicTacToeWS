package domain.usecase

import domain.GameRepository
import domain.models.GameSession
import kotlinx.coroutines.flow.Flow

class HostGameUseCase(
    private val gameRepository: GameRepository
) {

    suspend fun execute(): Result<Flow<GameSession?>> {
        return gameRepository.createGame()
    }

}