package org.example.project.data.mapper

import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.example.project.data.remote.dto.day_schedule.ScheduleData
import org.example.project.data.remote.dto.exam_schedule.ExamScheduleData
import org.example.project.data.remote.dto.semester_schedule.SemesterScheduleData
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.domain.model.DaySchedule
import org.example.project.domain.model.ScheduleType
import org.example.project.domain.model.SubjectItem
import kotlin.time.Clock

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

fun ScheduleData.toDaySchedule(): List<DaySchedule> {
    return this.courseClasses.map { it.toDaySchedule() }
}

private fun CourseClass.toDaySchedule(): DaySchedule {
    return DaySchedule(
        nameSubject = subjectName,
        location = room,
        startTime = startTime,
        endTime = endTime,
        type = ScheduleType.STUDY,
        lecturerName = lecturer.fullName
    )
}

fun ExamScheduleData.toDaySchedule(): List<DaySchedule> {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    return examSchedules
        .filter { it.localExamDate == today }
        .map { exam ->
            DaySchedule(
                nameSubject = exam.subjectName,
                location = "${exam.examLocation} - ${exam.examRoom}",
                startTime = exam.startTime,
                endTime = exam.endTime,
                type = ScheduleType.EXAM,
                examType = if (exam.examType == "GIUA_KY") "Giữa kỳ" else "Cuối kỳ"
            )
        }
        .sortedBy { it.startTime }
}