package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.remote.api.StudentApi
import org.example.project.data.remote.dto.me.StudentData
import org.example.project.domain.model.AppResult

class StudentRepository(
    private val studentApi: StudentApi
) {
    private val _studentInfo = MutableStateFlow<StudentData?>(null)
    val studentInfo = _studentInfo.asStateFlow()

    suspend fun getStudentInfo(): AppResult<StudentData> {
        return try {
            val data = studentApi.getStudentInfo().data
                ?: return AppResult.Failure(message = "Không có dữ liệu sinh viên")
            _studentInfo.value = data
            AppResult.Success(data = data)
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }
}