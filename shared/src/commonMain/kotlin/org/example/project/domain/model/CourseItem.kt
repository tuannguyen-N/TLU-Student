package org.example.project.domain.model

data class CourseItem(
    val code: String,
    val credits: Int,
    val name: String,
    val id: Int = 0,
    val semesterId: Int = 0,
    val status: CourseStatus = CourseStatus.AVAILABLE,
    val category: CourseFilter = CourseFilter.ALL
)
