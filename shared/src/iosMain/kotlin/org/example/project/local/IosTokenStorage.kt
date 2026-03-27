package org.example.project.local

import org.example.project.data.local.TokenStorage
import platform.Foundation.NSUserDefaults

class IosTokenStorage : TokenStorage {
    private val userDefaults = NSUserDefaults.standardUserDefaults
    private val KEY = "access_token"

    override fun saveAccessToken(token: String) {
        userDefaults.setObject(token, forKey = KEY)
    }

    override fun getAccessToken(): String {
        return userDefaults.stringForKey(KEY) ?: ""
    }

    override fun clearAccessToken() {
        userDefaults.removeObjectForKey(KEY)
    }
}