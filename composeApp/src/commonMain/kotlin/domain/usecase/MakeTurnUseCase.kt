package domain.usecase

import domain.GameRepository
import domain.models.Cell
import domain.models.GameSession

class MakeTurnUseCase(
    private val gameRepository: GameRepository
) {

    suspend fun execute(
        gameSession: GameSession,
        cellIndex: Int
    ): Result<Unit> {
        val field = gameSession.game.field.toMutableList()
        val rowIndex = cellIndex / field.size
        val column = cellIndex % field.size

        val row = field[rowIndex].toMutableList()
        row[column] = Cell(gameRepository.getPlayer().type)
        field[rowIndex] = row
        val newSession= gameSession.copy(
            game = gameSession.game.copy(field = field)
        )
        return gameRepository.sendGameSessionData(newSession)
    }
}