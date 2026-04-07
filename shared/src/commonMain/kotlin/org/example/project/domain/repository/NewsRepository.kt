package org.example.project.domain.repository

import org.example.project.data.mapper.toListUiModel
import org.example.project.data.remote.api.NewsApi
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.EventAndNewUiModel

class NewsRepository(
    private val api: NewsApi
) {
    suspend fun getTop5News(): AppResult<List<EventAndNewUiModel>> {
        return try {
            val data = api.getTop5News().data ?: return AppResult.Failure("Failed to get news")
            AppResult.Success(data.toListUiModel())
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }

    suspend fun getNews(): AppResult<List<EventAndNewUiModel>> {
        return try {
            val data = api.getNews().data ?: return AppResult.Failure("Failed to get news")
            AppResult.Success(data.content.toListUiModel())
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }
}