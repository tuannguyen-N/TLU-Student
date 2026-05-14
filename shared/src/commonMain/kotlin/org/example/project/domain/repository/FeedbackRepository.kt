package org.example.project.domain.repository

import org.example.project.data.remote.api.FeedbackApi
import org.example.project.data.remote.dto.feedback.FeedbackCategoryData
import org.example.project.data.remote.dto.Response
import org.example.project.domain.model.AppResult

class FeedbackRepository(
    private val api: FeedbackApi
) {

    suspend fun getCategory(): AppResult<List<FeedbackCategoryData>> {
        return try {
            val result = api.getCategory()
            if (result.data != null) {
                AppResult.Success(result.data)
            } else {
                AppResult.Failure(result.message)
            }
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }

    suspend fun sendFeedback(
        files: List<Pair<String, ByteArray>>,
        title: String,
        categoryId: Long,
        content: String,
        appVersion: String? = null,
        deviceInfo: String? = null
    ): AppResult<Unit> {
        return try {
            val result: Response = api.sendFeedback(files, title, categoryId, content, appVersion, deviceInfo)
            if (result.code == 0) {
                AppResult.Success(Unit)
            } else {
                AppResult.Failure(result.message ?: "Unknown error")
            }
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }

}