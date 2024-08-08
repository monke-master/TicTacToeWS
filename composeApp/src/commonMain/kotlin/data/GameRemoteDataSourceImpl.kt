package data

import domain.models.Game
import domain.models.GameSession
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.serialization.WebsocketContentConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import io.ktor.utils.io.charsets.Charset
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.serialization.sendSerializedBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.lighthousegames.logging.logging
import utils.getDeviceId

class GameRemoteDataSourceImpl(
    private val serializer: WebsocketContentConverter
): GameRemoteDataSource {

    private lateinit var session: DefaultClientWebSocketSession
    private var sessionFlow = MutableStateFlow<ServerResponse?>(null)


    override suspend fun hostGame(): Flow<ServerResponse?> {
        session = client.webSocketSession {
            url("$BASE_URL/$NEW_SESSION_ENDPOINT")
            parameter(DID_PARAM, getDeviceId())
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                session.incoming
                    .receiveAsFlow()
                    .map { (it as Frame.Text).readText() }
                    .collect { sessionFlow.value = Json.decodeFromString<ServerResponse>(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return sessionFlow
    }

    override suspend fun joinGame(code: String): Flow<ServerResponse?> {
        session = client.webSocketSession {
            url("$BASE_URL/$SESSION_ENDPOINT/$code")
            parameter(DID_PARAM, getDeviceId())
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                session
                    .incoming
                    .receiveAsFlow()
                    .map { (it as Frame.Text).readText() }
                    .collect {
                        logging("BEBRA").d { it }
                        sessionFlow.value = Json.decodeFromString<ServerResponse>(it)
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                sessionFlow.value = ServerResponse.Error(e.message ?: "Unknown error")
            }
        }
        return sessionFlow
    }

    override suspend fun closeConnection() {
        session.close()
        sessionFlow = MutableStateFlow(null)
    }

    @OptIn(InternalAPI::class)
    override suspend fun sendSessionData(sessionData: GameSession) {
        if (!session.isActive) {
            joinGame(sessionData.code)
        }

        session.sendSerializedBase<Game>(
            data = sessionData.game,
            converter = serializer,
            charset = Charset.forName("UTF-8")
        )
    }

    override suspend fun getSessionFlow(): Flow<ServerResponse?> = sessionFlow

    override suspend fun restartGame(code: String) {
        client.post {
            url("$BASE_URL_HTTP/$SESSION_ENDPOINT/$code/$RESTART_ENDPOINT")
        }
    }

    override suspend fun generateQr(code: String): ByteArray {
        return client.get {
            url("$BASE_URL_HTTP/$GENERATE_QR_ENDPOINT")
            parameter(CODE_PARAM, code)
        }.body()
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