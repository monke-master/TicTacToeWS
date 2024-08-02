package ui.join

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

lateinit var scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>

@Composable
actual fun prepareScanner(
    onResult: (String) -> Unit,
    onFailure: () -> Unit
) {
    scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result -> onResult(result.contents) }
    )
}



actual fun scanQrCode() {
    scanLauncher.launch(ScanOptions())
}