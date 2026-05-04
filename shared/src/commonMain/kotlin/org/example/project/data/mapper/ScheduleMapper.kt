package org.example.project.data.mapper

import org.example.project.data.remote.dto.semester_schedule.SemesterScheduleData
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.domain.model.SubjectItem

fun SemesterScheduleData.toSubjects(): List<SubjectItem> {
    return this.courseClasses.map { it.toSubjectItem() }
}

fun CourseClass.toSubjectItem(): SubjectItem {
    return SubjectItem(
        name = subjectName,
        credits = credits ?: 0,
        code = subjectCode,
    )
}