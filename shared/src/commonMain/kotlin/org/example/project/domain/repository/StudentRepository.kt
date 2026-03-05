package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.remote.api.StudentApi
import org.example.project.data.remote.dto.me.StudentInformation
import org.example.project.data.remote.dto.me.StudentInformationResponse

class StudentRepository(
    private val studentApi: StudentApi
) {
    private val _studentInfo = MutableStateFlow<StudentInformation?>(null)
    val studentInfo = _studentInfo.asStateFlow()

    suspend fun getStudentInfo(): Result<StudentInformationResponse> {
        return runCatching { studentApi.getStudentInfo() }
            .onSuccess { _studentInfo.value = it.data }
    }
}