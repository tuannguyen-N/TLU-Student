package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    suspend fun saveSearchHistory(userId: String, keyword: String)

    suspend fun getSearchHistory(userId: String): List<String>

    fun observeSearchHistory(userId: String): Flow<List<String>>

    suspend fun removeSearchHistory(userId: String, keyword: String)

    suspend fun clearSearchHistory(userId: String)
}