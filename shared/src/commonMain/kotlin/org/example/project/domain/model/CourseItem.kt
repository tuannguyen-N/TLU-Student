package org.example.project.domain.model

data class CourseItem(
    val code: String,
    val credits: Int,
    val name: String,
    val status: CourseStatus = CourseStatus.AVAILABLE,
    val category: CourseFilter = CourseFilter.ALL
) // TODO: Delete when finish()
