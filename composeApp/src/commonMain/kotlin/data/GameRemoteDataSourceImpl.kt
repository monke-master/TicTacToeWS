package data

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
import io.ktor.serialization.kotlinx.json.json
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.json.Json

class GameRemoteDataSourceImpl: GameRemoteDataSource {

    private lateinit var session: WebSocketSession

    override suspend fun hostGame(): Flow<String> {
        session = client.webSocketSession {
            url("ws://192.168.1.103:8080/sessions/new")
        }

        return session
            .incoming
            .receiveAsFlow()
            .map { (it as Frame.Text).readText() }
    }

    override suspend fun joinGame(code: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun closeConnection() {
        TODO("Not yet implemented")
    }

    private val client = HttpClient {
        install(WebSockets) {

        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }

//        install(DefaultRequest) {
//            url(BASE_URL)
//        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}