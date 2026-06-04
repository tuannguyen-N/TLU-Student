package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.remote.api.StudentApi
import org.example.project.data.remote.dto.me.StudentData
import org.example.project.data.remote.dto.student_search.StudentPageData
import org.example.project.domain.model.AppResult

class StudentRepository(
    private val studentApi: StudentApi
) {
    private val _studentInfo = MutableStateFlow<StudentData?>(null)
    val studentInfo = _studentInfo.asStateFlow()

    suspend fun getStudentInfo(): AppResult<StudentData> {
        return try {
            val data = studentApi.getStudentInfo().data
                ?: return AppResult.Failure(
                    message = "Không có dữ liệu sinh viên"
                )
            _studentInfo.value = data
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }

    suspend fun getStudentInfo(studentCode: String): AppResult<org.example.project.data.remote.dto.student_search.StudentData> {
        return try {
            val data = studentApi.getStudentInfo(studentCode.uppercase()).data
                ?: return AppResult.Failure(
                    message = "Không có dữ liệu sinh viên"
                )
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }

    suspend fun searchStudents(
        keyword: String,
        page: Int = 0,
        size: Int = 10
    ): AppResult<StudentPageData> {
        return try {
            val response = studentApi.searchStudents(
                search = keyword,
                page = page,
                size = size
            )

            val data =
                response.data ?: return AppResult.Failure(message = "Không tìm thấy sinh viên")
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }

    suspend fun getAllStudents(): AppResult<StudentPageData> {
        return try {
            val response = studentApi.searchStudents(size = 10000)

            val data =
                response.data ?: return AppResult.Failure(message = "Không tìm thấy sinh viên")
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }
}