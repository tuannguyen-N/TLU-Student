package org.example.project.presentations.screen.class_sign_up

import org.example.project.data.remote.dto.enroll.EnrollmentScheduleData
import org.example.project.data.remote.dto.enrollment_course_classes.CourseClassEnrollmentData
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.domain.model.CourseItem

data class ClassSignUpState(
    val isLoading: Boolean = false,
    val courses: List<CourseItem> = emptyList(),
    val error: String? = null,
    val showDialog: Boolean = false,
    val isDialogLoading: Boolean = false,
    val selectedCourseTitle: String = "",
    val totalSubjects: Int = 0,
    val totalCredits: Int = 0,
    val currentSemester: Semester? = null,
    val courseClasses: List<CourseClassEnrollmentData> = emptyList(),
    val enrolledClasses: List<EnrollmentScheduleData> = emptyList(),
    val enrollmentStartTime: String? = null,
    val enrollmentEndTime: String? = null,
)