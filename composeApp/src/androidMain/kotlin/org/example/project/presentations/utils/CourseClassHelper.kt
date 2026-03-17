package org.example.project.presentations.utils

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