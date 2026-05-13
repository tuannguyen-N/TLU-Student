package org.example.project.domain.repository

import org.example.project.data.remote.api.ApplicationApi
import org.example.project.data.remote.dto.application.ApplicationType
import org.example.project.data.remote.dto.application_history.ApplicationHistoryData
import org.example.project.domain.model.AppResult

class ApplicationRepository(
    private val api: ApplicationApi
) {
    suspend fun getApplicationTypes(): AppResult<List<ApplicationType>> {
        try {
            val data = api.getApplicationTypes().data ?: return AppResult.Failure("Data empty")
            return AppResult.Success(data)
        } catch (e: Exception) {
            return AppResult.Failure(e.message, e)
        }
    }

    suspend fun submitApplication(
        files: List<Pair<String, ByteArray>>,
        applicationType: Int,
        content: String? = null
    ): AppResult<List<String>> {
        return try {
            val result = api.submitApplication(files, applicationType, content)
            if (result.data != null) {
                AppResult.Success(result.data)
            } else {
                AppResult.Failure(result.message)
            }
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }

    suspend fun getApplicationHistory(): AppResult<List<ApplicationHistoryData>> {
        return try {
            val result = api.getApplicationHistory()
            if (result.data != null) {
                AppResult.Success(result.data)
            } else {
                AppResult.Failure(result.message)
            }
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }

    suspend fun getApplicationDetail(id: Int): AppResult<org.example.project.data.remote.dto.application_detail.ApplicationDetailData> {
        return try {
            val result = api.getApplicationDetail(id)
            if (result.data != null) {
                AppResult.Success(result.data)
            } else {
                AppResult.Failure(result.message)
            }
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }
}