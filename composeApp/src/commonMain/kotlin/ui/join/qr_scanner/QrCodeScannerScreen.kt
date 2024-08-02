package ui.join.qr_scanner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.publicvalue.multiplatform.qrcode.CodeType
import org.publicvalue.multiplatform.qrcode.ScannerWithPermissions
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun QrCodeScannerScreen(
    onScanned: (String) -> Unit
) {
    val rootController = LocalRootController.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ScannerWithPermissions(
            onScanned = {
                onScanned(it)
                rootController.popBackStack()
                true
            },
            types = listOf(CodeType.QR)
        )
    }

}