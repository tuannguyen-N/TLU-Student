package org.example.project.data.mapper

import org.example.project.data.remote.dto.transcript.AcademicResultData
import org.example.project.data.remote.dto.transcript.SemesterResult
import org.example.project.data.remote.dto.transcript.SemesterSummary
import org.example.project.data.remote.dto.transcript.SubjectResult
import org.example.project.domain.model.AcademicYearGroup
import org.example.project.domain.model.SemesterUiModel
import org.example.project.domain.model.SubjectResultUiModel
import org.example.project.domain.model.TranscriptUiModel
import kotlin.math.round

object TranscriptMapper {

    fun getGpa(transcript: AcademicResultData): Double {
        val gpa = transcript.semesterResults
            .map { it.semesterSummary?.semesterGpa ?: it.calculateSemesterSummary().semesterGpa }
            .average()
        return round(gpa * 100) / 100
    }

    fun getTotalCredit(transcript: AcademicResultData): Int {
        return transcript.semesterResults.sumOf { semesterResult ->
            semesterResult.semesterSummary?.creditsPassed ?: semesterResult.calculateSemesterSummary().creditsPassed
        }
    }

    fun AcademicResultData.toUiModel(): TranscriptUiModel {
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

        val totalCreditsPassed = semesterResults.sumOf { it.semesterSummary?.creditsPassed ?: it.calculateSemesterSummary().creditsPassed }

        return TranscriptUiModel(
            cumulativeGpa = getGpa(this),
            totalCreditsPassed = totalCreditsPassed,
            academicYearGroups = grouped
        )
    }

    private fun SemesterResult.toSemesterUiModel(): SemesterUiModel {
        val summary = semesterSummary ?: calculateSemesterSummary()
        return SemesterUiModel(
            semesterLabel = semester,
            semesterGpa = summary.semesterGpa,
            creditsPassed = summary.creditsPassed,
            academicYear = extractYear(),
            subjects = subjectResults.map { it.toSubjectUiModel() }
        )
    }

    private fun SemesterResult.calculateSemesterSummary(): SemesterSummary {
        val creditsRegistered = subjectResults.sumOf { it.credits }
        val creditsPassed = subjectResults.filter { it.isPass }.sumOf { it.credits }
        val totalScore = subjectResults.sumOf { it.score10 * it.credits }
        val semesterGpa = if (creditsRegistered > 0) totalScore / creditsRegistered else 0.0
        return SemesterSummary(
            creditsRegistered = creditsRegistered,
            creditsPassed = creditsPassed,
            semesterGpa = round(semesterGpa * 100) / 100,
            conductScore = 0
        )
    }

    private fun SubjectResult.toSubjectUiModel() = SubjectResultUiModel(
        subjectName = subjectName,
        subjectCode = subjectCode,
        credits = credits,
        attendanceScore = attendanceScore,
        midtermScore = midtermScore,
        finalScore = finalScore,
        score10 = score10,
        score4 = score4,
        letterGrade = letterGrade,
        isPass = isPass
    )

    fun String.extractYear(): Int = substringBefore("-").toIntOrNull() ?: 0

    // "HK2 2021-2022" → "2021-2022"
    private fun SemesterResult.extractYear(): String =
        semester.substringAfter(" ").trim()

    // "HK2 2021-2022" → 2
    private fun SemesterResult.extractSemesterNumber(): Int =
        semester.removePrefix("HK").substringBefore(" ").toIntOrNull() ?: 0

}