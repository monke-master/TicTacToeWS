package domain.usecase

import domain.GameRepository

class QuitGameUseCase(
    private val gameRepository: GameRepository
) {

    suspend fun execute() = gameRepository.quitGame()
}