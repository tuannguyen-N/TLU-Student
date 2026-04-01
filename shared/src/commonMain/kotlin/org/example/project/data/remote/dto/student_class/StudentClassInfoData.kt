package org.example.project.data.remote.dto.student_class

import kotlinx.serialization.Serializable
import org.example.project.data.remote.dto.week_schedule.Lecturer

@Serializable
data class StudentClassInfoData(
    val classCode: String,
    val majorName: String,
    val startYear: Int,
    val academicAdvisor: Lecturer,
    val students: List<Student>
)