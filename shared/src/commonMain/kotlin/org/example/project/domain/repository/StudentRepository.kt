package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.remote.api.StudentApi
import org.example.project.data.remote.dto.me.StudentData
import org.example.project.domain.model.ApiResult

class StudentRepository(
    private val studentApi: StudentApi
) {
    private val _studentInfo = MutableStateFlow<StudentData?>(null)
    val studentInfo = _studentInfo.asStateFlow()

    suspend fun getStudentInfo(): ApiResult<StudentData> {
        return try {
            val data = studentApi.getStudentInfo().data
                ?: return ApiResult.Failure(message = "Không có dữ liệu sinh viên")
            _studentInfo.value = data
            ApiResult.Success(data = data)
        } catch (e: Exception) {
            ApiResult.Failure(message = e.message, cause = e)
        }
    }
}