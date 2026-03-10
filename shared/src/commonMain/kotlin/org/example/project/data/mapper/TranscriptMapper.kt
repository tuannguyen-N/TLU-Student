package org.example.project.data.mapper

import org.example.project.data.remote.dto.transcript.SemesterResult
import org.example.project.data.remote.dto.transcript.SubjectResult
import org.example.project.data.remote.dto.transcript.Transcript
import org.example.project.domain.model.AcademicYearGroup
import org.example.project.domain.model.SemesterUiModel
import org.example.project.domain.model.SubjectResultUiModel
import org.example.project.domain.model.TranscriptUiModel
import kotlin.math.round

object TranscriptMapper {

    fun getGpa(transcript: Transcript): Double {
        val gpa = transcript.semesterResults
            .map { it.semesterSummary.semesterGpa }
            .average()

        return round(gpa * 100) / 100
    }

    fun getTotalCredit(transcript: Transcript): Int{
        return transcript.semesterResults.sumOf { semesterResult ->
            semesterResult.semesterSummary.creditsPassed
        }
    }

    fun Transcript.toUiModel(): TranscriptUiModel {
        val sorted = semesterResults.sortedWith(
            compareByDescending<SemesterResult> { it.extractYear() }
                .thenByDescending { it.extractSemesterNumber() }
        )

        val grouped = sorted
            .groupBy { it.extractYear() }
            .map { (year, semesters) ->
                AcademicYearGroup(
                    academicYear = year,
                    semesters = semesters.map { it.toSemesterUiModel() }
                )
            }

        val lastSummary = sorted.firstOrNull()?.semesterSummary
        val totalCreditsPassed = semesterResults.sumOf { it.semesterSummary.creditsPassed }

        return TranscriptUiModel(
            cumulativeGpa = lastSummary?.cumulativeGpa ?: 0.0,
            totalCreditsPassed = totalCreditsPassed,
            academicYearGroups = grouped
        )
    }

    private fun SemesterResult.toSemesterUiModel() = SemesterUiModel(
        semesterLabel = extractSemesterLabel(),
        semesterGpa = semesterSummary.semesterGpa,
        creditsPassed = semesterSummary.creditsPassed,
        academicYear = extractYear(),
        subjects = subjectResults.map { it.toSubjectUiModel() }
    )

    private fun SubjectResult.toSubjectUiModel() = SubjectResultUiModel(
        subjectName = subjectName,
        subjectCode = subjectCode,
        credits = credits,
        score10 = score10,
        score4 = score4,
        letterGrade = letterGrade,
        isPass = isPass
    )

    // "HK2 2021-2022" → "2021-2022"
    private fun SemesterResult.extractYear(): String =
        semester.substringAfter(" ").trim()

    // "HK2 2021-2022" → 2
    private fun SemesterResult.extractSemesterNumber(): Int =
        semester.removePrefix("HK").substringBefore(" ").toIntOrNull() ?: 0

    // "HK2 2021-2022" → "HK2"
    private fun SemesterResult.extractSemesterLabel(): String =
        semester.substringBefore(" ").trim()
}