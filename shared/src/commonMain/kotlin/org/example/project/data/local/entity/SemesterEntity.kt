package org.example.project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "semesters")
data class SemesterEntity(
    @PrimaryKey val id: Int,
    val semesterName: String,
    val semesterCode: String,
    val academicYears: String,
    val semesterNumber: Int,
    val startDate: String,
    val endDate: String,
    val isActive: Boolean
)
