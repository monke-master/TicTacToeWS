package domain.usecase

import domain.GameRepository

class GetCurrentPlayerUseCase(
    private val gameRepository: GameRepository
) {

    fun execute() = gameRepository.getPlayer()
}