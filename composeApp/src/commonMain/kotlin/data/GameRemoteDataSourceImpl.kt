package data

import domain.models.Game
import domain.models.GameSession
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.serialization.WebsocketContentConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import io.ktor.utils.io.charsets.Charset
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import io.ktor.websocket.serialization.sendSerializedBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.json.Json

class GameRemoteDataSourceImpl(
    private val serializer: WebsocketContentConverter
): GameRemoteDataSource {

    private lateinit var session: WebSocketSession
    private lateinit var sessionFlow: Flow<String>

    override suspend fun hostGame(): Flow<String> {
        session = client.webSocketSession {
            url("$BASE_URL/$NEW_SESSION_ENDPOINT")
        }

        sessionFlow = session
            .incoming
            .receiveAsFlow()
            .map { (it as Frame.Text).readText() }
        return sessionFlow
    }

    override suspend fun joinGame(code: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun closeConnection() {
        TODO("Not yet implemented")
    }

    @OptIn(InternalAPI::class)
    override suspend fun sendSessionData(sessionData: GameSession) {
        session.sendSerializedBase<Game>(
            data = sessionData.game,
            converter = serializer,
            charset = Charset.forName("UTF-8")
        )
    }

    private val client = HttpClient {
        install(WebSockets) {

        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}