package domain.usecase

import domain.GameRepository

class GetGameSessionUseCase(
    private val gameRepository: GameRepository
) {

    suspend fun execute() = gameRepository.getSessionFlow()
}