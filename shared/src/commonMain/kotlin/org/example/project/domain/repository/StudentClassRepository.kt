package org.example.project.domain.repository

import org.example.project.data.remote.api.StudentClassApi
import org.example.project.data.remote.dto.student_class.StudentClassInfoData
import org.example.project.domain.model.AppResult

class StudentClassRepository(
    private val api: StudentClassApi
) {
    suspend fun getStudentClassInfo(): AppResult<StudentClassInfoData>{
        try {
            val data  = api.getStudentClassInfo().data ?: return AppResult.Failure("Không có dữ liệu")
            return AppResult.Success(data = data)
        }catch (e: Exception){
            return AppResult.Failure(message = e.message, cause = e)
        }
    }
}