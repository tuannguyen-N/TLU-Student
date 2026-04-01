package org.example.project.domain.repository

import org.example.project.data.remote.api.StudentClassApi
import org.example.project.data.remote.dto.student_class.StudentClassInfoData
import org.example.project.domain.model.ApiResult

class StudentClassRepository(
    private val api: StudentClassApi
) {
    suspend fun getStudentClassInfo(): ApiResult<StudentClassInfoData>{
        try {
            val data  = api.getStudentClassInfo().data ?: return ApiResult.Failure("Không có dữ liệu")
            return ApiResult.Success(data = data)
        }catch (e: Exception){
            return ApiResult.Failure(message = e.message, cause = e)
        }
    }
}