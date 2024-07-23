package data

import kotlinx.coroutines.flow.Flow

interface GameRemoteDataSource {

    suspend fun hostGame(): Flow<String>

    suspend fun joinGame(code: String): Result<Unit>

    suspend fun closeConnection()
}