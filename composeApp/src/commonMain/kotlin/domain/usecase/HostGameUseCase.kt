package domain.usecase

import data.ServerResponse
import domain.GameRepository
import domain.models.GameSession
import kotlinx.coroutines.flow.Flow

class HostGameUseCase(
    private val gameRepository: GameRepository
) {

    suspend fun execute(): Result<Flow<ServerResponse?>> {
        return gameRepository.createGame()
    }

}