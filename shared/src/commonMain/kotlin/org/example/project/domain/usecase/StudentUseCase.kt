package org.example.project.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import org.example.project.data.remote.dto.me.StudentData
import org.example.project.data.remote.dto.student_search.StudentPageData
import org.example.project.domain.model.AppResult
import org.example.project.domain.repository.StudentRepository

class StudentUseCase(
    private val repository: StudentRepository
) {
    val studentInfo: StateFlow<StudentData?> = repository.studentInfo

    suspend fun getStudentInfo(): AppResult<StudentData> = repository.getStudentInfo()

    suspend fun searchStudents(
        keyword: String,
        page: Int = 0,
        size: Int = 10
    ): AppResult<StudentPageData> = repository.searchStudents(keyword, page, size)

    suspend fun getAllStudents(): AppResult<StudentPageData> = repository.getAllStudents()
}