package ui.join

import androidx.compose.runtime.Composable

@Composable
expect fun PrepareScanner(
    onResult: (String) -> Unit,
    onFailure: () -> Unit
)

expect fun scanQrCode()