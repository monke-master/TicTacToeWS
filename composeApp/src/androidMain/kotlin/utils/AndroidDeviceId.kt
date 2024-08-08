package utils

import android.content.ContentResolver
import android.provider.Settings

object AndroidDeviceId {

    lateinit var contentResolver: ContentResolver
}

actual fun getDeviceId() = Settings.System.getString(AndroidDeviceId.contentResolver, Settings.Secure.ANDROID_ID)