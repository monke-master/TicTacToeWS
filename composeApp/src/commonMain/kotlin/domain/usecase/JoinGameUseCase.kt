package domain.usecase

import domain.GameRepository

class JoinGameUseCase(
    private val gameRepository: GameRepository
) {

    suspend fun execute(code: String) = gameRepository.joinGame(code)
}