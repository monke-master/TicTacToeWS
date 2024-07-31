package domain.usecase

import domain.GameRepository

class JoinGameUseCase(
    private val userRepository: GameRepository
) {

    suspend fun execute(code: String) = userRepository.joinGame(code)
}