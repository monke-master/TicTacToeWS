import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.send
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.lighthousegames.logging.logging



lateinit var socketSession: SocketSession


@Composable
@Preview
fun App() {


    var messages by remember {
        mutableStateOf(listOf(""))
    }

    LaunchedEffect(Unit) {
        socketSession = SocketSession()
        socketSession.openSession {
            messages += it
        }
    }


    MaterialTheme {
        Column {
            LazyColumn {
                items(messages) {
                    Text(it)
                }
            }
            Button(
                onClick = { sendMessage() }
            ) {
                Text("Send Message")
            }
        }
    }
}

private fun sendMessage() {
    GlobalScope.launch {
        withContext(Dispatchers.IO) {
            socketSession.send("Hello niggers from" + getPlatform())
        }
    }
}