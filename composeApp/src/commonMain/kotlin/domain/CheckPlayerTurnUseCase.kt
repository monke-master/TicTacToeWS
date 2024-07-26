package domain

import domain.models.GameSession

class CheckPlayerTurnUseCase (
    private val gameRepository: GameRepository
) {

    fun execute(gameSession: GameSession): Boolean {
        return gameSession.game.turn.playerId == gameRepository.getPlayer().id
    }
}