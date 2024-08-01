package utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual fun ByteArray.toBitmap(): ImageBitmap {
    return Image.makeFromEncoded(this).toComposeImageBitmap()
}