package org.example.project.data.remote.dto.student_class

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val studentCode: String,
    val fullName: String,
    val gender: String,
    val avatarUrl: String?,
//    val position: String
)