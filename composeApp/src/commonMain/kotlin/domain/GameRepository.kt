package domain

import data.ServerResponse
import domain.models.GameSession
import domain.models.Player
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    suspend fun createGame(): Result<Flow<ServerResponse?>>

    suspend fun joinGame(code: String): Result<Flow<ServerResponse?>>

    suspend fun getSessionFlow(): Result<Flow<ServerResponse?>>

    suspend fun sendGameSessionData(gameSession: GameSession): Result<Unit>

    fun getPlayer(): Player

    suspend fun quitGame(): Result<Unit>

    suspend fun restartGame(code: String): Result<Unit>

    suspend fun generateQr(code: String): Result<ByteArray>
}