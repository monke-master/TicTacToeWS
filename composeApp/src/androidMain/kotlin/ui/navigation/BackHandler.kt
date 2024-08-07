package ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun registerOnBackCallback(callback: () -> Unit) {
    BackHandler {
        callback()
    }
}