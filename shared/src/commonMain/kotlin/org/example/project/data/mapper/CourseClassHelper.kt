package org.example.project.data.mapper

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.example.project.data.remote.dto.week_schedule.CourseClass

fun List<CourseClass>.nearestClasses(
    currentTime: LocalTime,
    limit: Int = 3
): List<CourseClass> {

    val sorted = sortedBy { it.startTime }

    val index = sorted.indexOfFirst {
        LocalTime.parse(it.endTime) > currentTime
    }.coerceAtLeast(0)

    return sorted.drop(index).take(limit)
}

fun CourseClass.getClassStatus(currentTime: LocalTime): ClassStatus {
    val start = LocalTime.parse(startTime) // "07:00"
    val end = LocalTime.parse(endTime)     // "09:30"

    return when {
        currentTime < start -> ClassStatus.UPCOMING
        currentTime > end   -> ClassStatus.FINISHED
        else                -> ClassStatus.IN_PROGRESS
    }
}

enum class ClassStatus { UPCOMING, IN_PROGRESS, FINISHED }