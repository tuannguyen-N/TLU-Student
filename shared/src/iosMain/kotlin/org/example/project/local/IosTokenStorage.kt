package org.example.project.local

import org.example.project.data.local.TokenStorage
import platform.Foundation.NSUserDefaults

class IosTokenStorage : TokenStorage {
    private val userDefaults = NSUserDefaults.standardUserDefaults
    private val KEY_ACCESS = "access_token"
    private val KEY_REFRESH = "refresh_token"

    override fun saveAccessToken(token: String) {
        userDefaults.setObject(token, forKey = KEY_ACCESS)
    }

    override fun getAccessToken(): String? {
        return userDefaults.stringForKey(KEY_ACCESS)
    }

    override fun clearAccessToken() {
        userDefaults.removeObjectForKey(KEY_ACCESS)
    }

    override fun saveRefreshToken(token: String) {
        userDefaults.setObject(token, forKey = KEY_REFRESH)
    }

    override fun getRefreshToken(): String? {
        return userDefaults.stringForKey(KEY_REFRESH)
    }

    override fun clearRefreshToken() {
        userDefaults.removeObjectForKey(KEY_REFRESH)
    }
}