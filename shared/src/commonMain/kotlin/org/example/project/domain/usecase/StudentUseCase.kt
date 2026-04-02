package org.example.project.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import org.example.project.data.remote.dto.me.StudentData
import org.example.project.domain.model.AppResult
import org.example.project.domain.repository.StudentRepository

class StudentUseCase(
    private val repository: StudentRepository
) {
    val studentInfo: StateFlow<StudentData?> = repository.studentInfo

    suspend fun getStudentInfo(): AppResult<StudentData> = repository.getStudentInfo()
}