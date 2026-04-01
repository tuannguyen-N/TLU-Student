package org.example.project.presentations.screen.student_class

import org.example.project.data.remote.dto.student_class.StudentClassInfoData

data class StudentClassState(
    val studentClassInfoData: StudentClassInfoData? = null,
    val isLoading: Boolean = false
)