package org.example.project.local

import android.content.SharedPreferences
import org.example.project.data.local.AppPreferences
import androidx.core.content.edit

class AndroidAppPreferences(
    private val prefs: SharedPreferences
) : AppPreferences {

    companion object {
        private const val KEY = "notification_permission_asked"
    }

    override fun setNotificationPermissionAsked(value: Boolean) {
        prefs.edit { putBoolean(KEY, value) }
    }

    override fun isNotificationPermissionAsked(): Boolean {
        return prefs.getBoolean(KEY, false)
    }
}