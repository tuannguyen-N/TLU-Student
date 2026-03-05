package org.example.project.data.local

interface TokenStorage {
    fun saveAccessToken(token: String)
    fun getAccessToken(): String?
}