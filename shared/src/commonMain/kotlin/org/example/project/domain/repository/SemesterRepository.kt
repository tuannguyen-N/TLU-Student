package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.remote.api.SemesterApi
import org.example.project.data.remote.dto.semester.Semester

class SemesterRepository(
    private val semesterApi: SemesterApi
) {
    private val _semesters = MutableStateFlow<List<Semester>>(emptyList())
    val semesters = _semesters.asStateFlow()

    suspend fun getSemesters(): Result<List<Semester>> {
        return runCatching {
            semesterApi.getSemesters()
        }.onSuccess { _semesters.value = it }
    }
}