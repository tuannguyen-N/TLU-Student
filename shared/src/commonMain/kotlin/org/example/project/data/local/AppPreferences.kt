package org.example.project.data.local

interface AppPreferences {
    fun setNotificationPermissionAsked(value: Boolean)
    fun isNotificationPermissionAsked(): Boolean
}