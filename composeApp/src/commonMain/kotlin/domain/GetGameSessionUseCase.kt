package domain

class GetGameSessionUseCase(
    private val gameRepository: GameRepository
) {

    suspend fun execute() = gameRepository.getSessionFlow()
}