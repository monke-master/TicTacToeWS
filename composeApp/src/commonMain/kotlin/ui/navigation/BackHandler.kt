package ui.navigation

import androidx.compose.runtime.Composable

@Composable
expect fun registerOnBackCallback(callback: () -> Unit)