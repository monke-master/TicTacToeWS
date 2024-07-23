package domain

import domain.models.GameSession
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    suspend fun createGame(): Result<Flow<GameSession?>>

    suspend fun getSessionFlow(): Flow<GameSession?>

    suspend fun sendGameSessionData(gameSession: GameSession): Result<Unit>
}