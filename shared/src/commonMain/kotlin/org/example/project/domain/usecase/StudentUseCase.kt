package org.example.project.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import org.example.project.data.remote.dto.me.StudentInformation
import org.example.project.data.remote.dto.me.StudentInformationResponse
import org.example.project.domain.repository.StudentRepository

class StudentUseCase(
    private val repository: StudentRepository
) {
    val studentInfo: StateFlow<StudentInformation?> = repository.studentInfo

    suspend fun getStudentInfo(): Result<StudentInformationResponse> = repository.getStudentInfo()
}