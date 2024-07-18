import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.ui.tooling.preview.Preview


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