package data

import domain.models.GameSession
import kotlinx.coroutines.flow.Flow

interface GameRemoteDataSource {

    suspend fun hostGame(): Flow<String?>

    suspend fun joinGame(code: String): Flow<String?>

    suspend fun closeConnection()

    suspend fun sendSessionData(sessionData: GameSession)

    suspend fun getSessionFlow(): Flow<String?>

    suspend fun restartGame(code: String)

    suspend fun generateQr(code: String): ByteArray
}