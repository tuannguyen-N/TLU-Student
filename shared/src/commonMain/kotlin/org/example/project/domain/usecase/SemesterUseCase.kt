package org.example.project.domain.usecase

import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.domain.repository.SemesterRepository
import kotlin.time.Clock

class SemesterUseCase(
    private val semesterRepository: SemesterRepository
) {
    val semesters = semesterRepository.semesters.map {
        it?.filter { semester -> checkingAvailableDate(semester.startDate) }
    }

    suspend fun getSemesters(): Result<List<Semester>?> {
        return semesterRepository.getSemesters().map { it.filter { semester ->
            checkingAvailableDate(semester.startDate)
        } }
    }

    private fun checkingAvailableDate(startDate: String): Boolean {
        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        val start = LocalDate.parse(startDate)
        return today >= start
    }
}