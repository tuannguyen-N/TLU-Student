package org.example.project.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import org.example.project.data.remote.dto.me.StudentData
import org.example.project.data.remote.dto.me.StudentInformationResponse
import org.example.project.domain.model.ApiResult
import org.example.project.domain.repository.StudentRepository

class StudentUseCase(
    private val repository: StudentRepository
) {
    val studentInfo: StateFlow<StudentData?> = repository.studentInfo

    suspend fun getStudentInfo(): ApiResult<StudentData> = repository.getStudentInfo()
}