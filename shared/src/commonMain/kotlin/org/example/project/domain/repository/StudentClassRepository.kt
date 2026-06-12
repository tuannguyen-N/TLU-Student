package org.example.project.domain.repository

import org.example.project.data.remote.api.StudentClassApi
import org.example.project.data.remote.dto.student_class.StudentClassInfoData
import org.example.project.domain.model.AppResult

class StudentClassRepository(
    private val api: StudentClassApi
) {
    suspend fun getStudentClassInfo(): AppResult<StudentClassInfoData>{
        try {
            val data  = api.getStudentClassInfo()
            if (data.data == null){
                return AppResult.Failure(message = data.message)
            }
            return AppResult.Success(data = data.data)
        }catch (e: Exception){
            return AppResult.Failure(message = e.message, cause = e)
        }
    }
}