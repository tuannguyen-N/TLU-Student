package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.remote.api.SemesterApi
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.data.remote.dto.semester.SemesterResponse
import org.example.project.domain.model.ApiResult

class SemesterRepository(
    private val semesterApi: SemesterApi
) {
    private val _semesters = MutableStateFlow<List<Semester>?>(null)
    val semesters = _semesters.asStateFlow()

    suspend fun getSemesters(): ApiResult<List<Semester>> {
        return try {
            val data = semesterApi.getSemesters().data
                ?: return ApiResult.Failure(message = "Không có dữ liệu học kỳ")

            _semesters.value = data
            ApiResult.Success(data)
        } catch (e: Exception) {
            ApiResult.Failure(message = e.message, cause = e)
        }
    }
}