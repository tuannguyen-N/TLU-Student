package org.example.project.data.mapper

import org.example.project.data.remote.dto.semester_schedule.SemesterSchedule
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.domain.model.SubjectItem

fun SemesterSchedule.toSubjects(): List<SubjectItem>{
    return this.courseClasses.map { it.toSubjectItem() }
}

fun CourseClass.toSubjectItem() : SubjectItem{
    return SubjectItem(
        name = subjectName,
        credits = 3,
        code = subjectCode,
    )
}
// TODO: continue