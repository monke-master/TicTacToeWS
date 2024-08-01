package data

import domain.GameRepository
import domain.models.GameSession
import domain.models.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.lighthousegames.logging.logging

private const val TAG = "GameRepositoryImpl"

class GameRepositoryImpl(
    private val gameRemoteDataSource: GameRemoteDataSource,
    private val gameLocalDataSource: GameLocalDataSource
): GameRepository {

    override suspend fun createGame(): Result<Flow<GameSession?>> {
        return withContext(Dispatchers.IO) {
            try {
                val flow = gameRemoteDataSource.hostGame()
                return@withContext Result.success(
                    flow.map { data ->
                        data?.let {
                            val session = Json.decodeFromString<GameSession>(data)
                            gameLocalDataSource.setPlayer(session.players[0])
                            session
                        }
                    }
                )
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override suspend fun getSessionFlow(): Result<Flow<GameSession?>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext Result.success(
                    gameRemoteDataSource
                        .getSessionFlow()
                        .map {
                            it?.let {
                                Json.decodeFromString<GameSession>(it)
                            }
                        }
                )
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override suspend fun sendGameSessionData(gameSession: GameSession): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                gameRemoteDataSource.sendSessionData(gameSession)
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override suspend fun joinGame(code: String): Result<Flow<GameSession?>> {
        return withContext(Dispatchers.IO) {
            try {
                val flow = gameRemoteDataSource.joinGame(code)
                return@withContext Result.success(
                    flow.map { data ->
                        data?.let {
                            val session = Json.decodeFromString<GameSession>(data)
                            logging(TAG).d { session }
                            gameLocalDataSource.setPlayer(session.players[1])
                            session
                        }
                    }
                )
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override fun getPlayer(): Player = gameLocalDataSource.getPlayer()

    override suspend fun quitGame(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                gameRemoteDataSource.closeConnection()
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override suspend fun restartGame(code: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                gameRemoteDataSource.restartGame(code)
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }
}