package data

import domain.GameRepository
import domain.models.GameSession
import domain.models.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.lighthousegames.logging.logging
import utils.Dispatcher

private const val TAG = "GameRepositoryImpl"

class GameRepositoryImpl(
    private val gameRemoteDataSource: GameRemoteDataSource,
    private val gameLocalDataSource: GameLocalDataSource,
    private val dispatcher: Dispatcher
): GameRepository {

    override suspend fun createGame(): Result<Flow<ServerResponse?>> {
        return withContext(dispatcher.IO) {
            try {
                val flow = gameRemoteDataSource.hostGame()
                return@withContext Result.success(
                    flow.map { data ->
                        if (data is ServerResponse.Success) {
                            gameLocalDataSource.setPlayer(data.gameSession.players[0])
                        }
                        data
                    }
                )
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override suspend fun getSessionFlow(): Result<Flow<ServerResponse?>> {
        return withContext(dispatcher.IO) {
            try {
                return@withContext Result.success(gameRemoteDataSource.getSessionFlow())
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override suspend fun sendGameSessionData(gameSession: GameSession): Result<Unit> {
        return withContext(dispatcher.IO) {
            try {
                gameRemoteDataSource.sendSessionData(gameSession)
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override suspend fun joinGame(code: String): Result<Flow<ServerResponse?>> {
        return withContext(dispatcher.IO) {
            try {
                val flow = gameRemoteDataSource.joinGame(code)
                return@withContext Result.success(
                    flow.map { data ->
                        if (data is ServerResponse.Success) {
                            gameLocalDataSource.setPlayer(data.gameSession.players[1])
                        }
                        data
                    }
                )
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override fun getPlayer(): Player = gameLocalDataSource.getPlayer()

    override suspend fun quitGame(): Result<Unit> {
        return withContext(dispatcher.IO) {
            try {
                gameRemoteDataSource.closeConnection()
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override suspend fun restartGame(code: String): Result<Unit> {
        return withContext(dispatcher.IO) {
            try {
                gameRemoteDataSource.restartGame(code)
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override suspend fun generateQr(code: String): Result<ByteArray> {
        return withContext(dispatcher.IO) {
            try {
                return@withContext Result.success(gameRemoteDataSource.generateQr(code))
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }
}