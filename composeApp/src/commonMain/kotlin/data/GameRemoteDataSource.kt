package data

import domain.models.GameSession
import kotlinx.coroutines.flow.Flow

interface GameRemoteDataSource {

    suspend fun hostGame(): Flow<ServerResponse?>

    suspend fun joinGame(code: String): Flow<ServerResponse?>

    suspend fun closeConnection()

    suspend fun sendSessionData(sessionData: GameSession)

    suspend fun getSessionFlow(): Flow<ServerResponse?>

    suspend fun restartGame(code: String)

    suspend fun generateQr(code: String): ByteArray
}