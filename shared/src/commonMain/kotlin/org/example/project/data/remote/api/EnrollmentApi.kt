package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.remote.dto.Response
import org.example.project.data.remote.dto.enroll.CourseEnrollmentResponse
import org.example.project.data.remote.dto.enroll.EnrollmentScheduleResponse
import org.example.project.data.remote.dto.enrollment_course_classes.CourseClassesEnrollmentResponse

class EnrollmentApi(
    private val client: HttpClient
) {
    suspend fun getAllCourseEnrollment(studyProgramCode: String): CourseEnrollmentResponse {
        return client.get("/api/v1/student/enrollment/all") {
            parameter("studyProgramCode", studyProgramCode)
        }.body()
    }

    suspend fun getSubjectEnrollment(
        subjectId: Int,
        semesterId: Int
    ): CourseClassesEnrollmentResponse {
        return client.post("/api/v1/student/enrollment/course-classes") {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "subjectId" to subjectId,
                    "semesterId" to semesterId
                )
            )
        }.body()
    }

    suspend fun enrollClass(studyProgramId: Int, courseClassId: Int): Response {
        return client.post("/api/v1/student/enrollment/enroll") {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "studyProgramId" to studyProgramId,
                    "courseClassId" to courseClassId
                )
            )
        }.body()
    }

    suspend fun getEnrollmentSchedule(semesterId: Int): EnrollmentScheduleResponse {
        return client.post("/api/v1/student/enrollment/schedule") {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "semesterId" to semesterId
                )
            )
        }.body()
    }

    suspend fun cancelEnrollmentClass(courseClassId: Int): Response {
        return client.post("/api/v1/student/enrollment/drop") {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "courseClassId" to courseClassId
                )
            )
        }.body()
    }
}