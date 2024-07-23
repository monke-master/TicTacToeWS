package data

import domain.GameRepository
import domain.models.GameSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.lighthousegames.logging.logging

private const val TAG = "GameRepositoryImpl"

class GameRepositoryImpl(
    private val gameRemoteDataSource: GameRemoteDataSource
): GameRepository {

    private val flow = MutableStateFlow<GameSession?>(null)

    override suspend fun createGame(): Result<Flow<GameSession>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext Result.success(
                    gameRemoteDataSource
                        .hostGame()
                        .map { Json.decodeFromString<GameSession>(it) }
                )
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override suspend fun getSessionFlow(): Flow<GameSession?> = flow


}