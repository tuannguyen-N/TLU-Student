package org.example.project.domain.repository

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import org.example.project.data.mapper.ErrorMapper
import org.example.project.data.remote.api.EnrollmentApi
import org.example.project.data.remote.api.StudyProgramApi
import org.example.project.data.remote.dto.enroll.ConflictScheduleData
import org.example.project.data.remote.dto.enroll.CourseEnrollmentData
import org.example.project.data.remote.dto.enroll.EnrollmentScheduleData
import org.example.project.data.remote.dto.enroll.PrerequisiteInfo
import org.example.project.data.remote.dto.enrollment_course_classes.CourseClassEnrollmentData
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.ErrorResponse

class EnrollmentRepository(
    private val api: EnrollmentApi,
    private val studyProgramApi: StudyProgramApi
) {
    private val _enrolledClasses = MutableStateFlow<List<EnrollmentScheduleData>>(emptyList())
    val enrolledClasses = _enrolledClasses.asStateFlow()

    suspend fun getAllCourseEnrollment(): AppResult<CourseEnrollmentData> {
        try {
            val studyProgram = studyProgramApi.getStudyPrograms().data?.firstOrNull()
                ?: return AppResult.Failure(message = "Không có dữ liệu học kỳ")

            val result = api.getAllCourseEnrollment(studyProgram.studyProgramCode)

            return if (result.data == null) {
                AppResult.Failure(
                    message = ErrorMapper.mapEnrollment(
                        result.code,
                        result.message
                    ).messageVi
                )
            } else {
                AppResult.Success(result.data)
            }
        } catch (e: ClientRequestException) {
            val body = e.response.bodyAsText()
            val error = Json.decodeFromString<ErrorResponse>(body)
            return AppResult.Failure(
                message = ErrorMapper.mapEnrollment(
                    error.code,
                    error.message
                ).messageVi
            )
        }
    }

    suspend fun getSubjectEnrollment(
        subjectId: Int,
        semesterId: Int
    ): AppResult<List<CourseClassEnrollmentData>> {
        try {
            val result = api.getSubjectEnrollment(subjectId, semesterId)
            return if (result.data == null) {
                AppResult.Failure(
                    message = ErrorMapper.mapEnrollment(
                        result.code,
                        result.message
                    ).messageVi
                )
            } else {
                AppResult.Success(result.data)
            }
        } catch (e: ClientRequestException) {
            val body = e.response.bodyAsText()
            val error = Json.decodeFromString<ErrorResponse>(body)
            return AppResult.Failure(
                message = ErrorMapper.mapEnrollment(
                    error.code,
                    error.message
                ).messageVi
            )
        }
    }

    suspend fun enrollClass(
        studyProgramId: Int,
        courseClassId: Int
    ): AppResult<String> {
        val result = api.enrollClass(studyProgramId, courseClassId)
        return when (result.code) {
            -102 -> {
                val prerequisites =
                    Json.decodeFromJsonElement<List<PrerequisiteInfo>>(result.data!!)
                val missingSubjects = prerequisites
                    .flatMap { it.missingSubjectCodes }
                    .distinct()
                    .joinToString(", ")
                AppResult.Failure(
                    message = "Bạn chưa đạt điều kiện tiên quyết. Cần hoàn thành các môn: $missingSubjects"
                )
            }

            -101 -> {
                val conflictData =
                    Json.decodeFromJsonElement<ConflictScheduleData>(result.data!!)
                val dayLabel = mapDayOfWeek(conflictData.dayOfWeek)
                AppResult.Failure(
                    message = "Trùng lịch học với lớp ${conflictData.classOverlapCode} " +
                            "($dayLabel, tiết ${conflictData.startPeriod}-${conflictData.endPeriod})"
                )
            }

            0 -> AppResult.Success(result.message)
            else -> AppResult.Failure(
                message = ErrorMapper.mapEnrollment(result.code, result.message).messageVi
            )
        }
    }

    private fun mapDayOfWeek(day: Int): String = when (day) {
        2 -> "Thứ 2"
        3 -> "Thứ 3"
        4 -> "Thứ 4"
        5 -> "Thứ 5"
        6 -> "Thứ 6"
        7 -> "Thứ 7"
        8 -> "Chủ nhật"
        else -> "Thứ $day"
    }

    suspend fun getEnrollmentSchedule(semesterId: Int): AppResult<List<EnrollmentScheduleData>> {
        try {
            val result = api.getEnrollmentSchedule(semesterId)
            return if (result.data != null) {
                _enrolledClasses.value = result.data
                AppResult.Success(result.data)
            } else {
                AppResult.Failure(
                    message = ErrorMapper.mapEnrollment(
                        result.code,
                        result.message
                    ).messageVi
                )
            }
        } catch (e: ClientRequestException) {
            val body = e.response.bodyAsText()
            val error = Json.decodeFromString<ErrorResponse>(body)
            return AppResult.Failure(
                message = ErrorMapper.mapEnrollment(
                    error.code,
                    error.message
                ).messageVi
            )
        }
    }

    suspend fun cancelEnrollmentClass(courseClassId: Int): AppResult<String> {
        try {
            val result = api.cancelEnrollmentClass(courseClassId)
            return if (result.code != 0) {
                AppResult.Failure(
                    message = ErrorMapper.mapEnrollment(
                        result.code,
                        result.message
                    ).messageVi
                )
            } else {
                AppResult.Success(result.message)
            }
        } catch (e: ClientRequestException) {
            val body = e.response.bodyAsText()
            val error = Json.decodeFromString<ErrorResponse>(body)
            return AppResult.Failure(
                message = ErrorMapper.mapEnrollment(
                    error.code,
                    error.message
                ).messageVi
            )
        }
    }
}