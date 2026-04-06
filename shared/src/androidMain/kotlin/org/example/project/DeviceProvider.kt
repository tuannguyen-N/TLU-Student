package org.example.project

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

actual class DeviceProvider(private val context: Context) {
    @SuppressLint("HardwareIds")
    actual fun getDeviceId(): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
}