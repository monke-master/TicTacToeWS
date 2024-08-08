package utils

import platform.UIKit.UIDevice

actual fun getDeviceId() = UIDevice.currentDevice.identifierForVendor?.UUIDString ?: "Unknown"