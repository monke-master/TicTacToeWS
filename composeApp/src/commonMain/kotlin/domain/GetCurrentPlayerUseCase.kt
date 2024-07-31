package domain

class GetCurrentPlayerUseCase(
    private val gameRepository: GameRepository
) {

    fun execute() = gameRepository.getPlayer()
}