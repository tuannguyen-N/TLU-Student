package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.mapper.ErrorMapper
import org.example.project.data.remote.api.EnrollmentApi
import org.example.project.data.remote.api.StudyProgramApi
import org.example.project.data.remote.dto.enroll.CourseEnrollmentData
import org.example.project.data.remote.dto.enroll.EnrollmentScheduleData
import org.example.project.data.remote.dto.enrollment_course_classes.CourseClassEnrollmentData
import org.example.project.domain.model.AppResult

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
        } catch (e: Exception) {
            return AppResult.Failure(message = e.message, cause = e)
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
        } catch (e: Exception) {
            return AppResult.Failure(message = e.message, cause = e)
        }
    }

    suspend fun enrollClass(studyProgramId: Int, courseClassId: Int): AppResult<String> {
        try {
            val result = api.enrollClass(studyProgramId, courseClassId)
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
        } catch (e: Exception) {
            return AppResult.Failure(message = e.message, cause = e)
        }
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
        } catch (e: Exception) {
            return AppResult.Failure(message = e.message, cause = e)
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
        } catch (e: Exception) {
            return AppResult.Failure(message = e.message, cause = e)
        }
    }
}