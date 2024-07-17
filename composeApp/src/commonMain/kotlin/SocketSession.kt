import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.http.HttpMethod
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val client = HttpClient {
    install(WebSockets)
}

class SocketSession {

    private var session: WebSocketSession? = null

    fun openSession(onReceived: (String) -> Unit) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                if (session == null) {
                    session = client.webSocketSession(
                        method = HttpMethod.Get,
                        host = "10.0.2.2", port = 8080, path = "/ws"
                    ) {

                    }
                }

                session!!
                    .incoming
                    .consumeAsFlow()
                    .filterIsInstance<Frame.Text>()
                    .mapNotNull {
                        it.readText()
                    }.collect {
                        onReceived(it)
                    }
            }
        }
    }


    fun send(message: String) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                session!!.send(message)
            }
        }
    }
}