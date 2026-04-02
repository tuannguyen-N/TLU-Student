package org.example.project.data.mapper

import org.example.project.data.local.entity.WeeklyScheduleEntity
import org.example.project.data.remote.dto.week_schedule.WeeklyScheduleData

fun WeeklyScheduleData.toWeeklyScheduleEntity(): WeeklyScheduleEntity {
    return WeeklyScheduleEntity(
        id = "$startDate - $endDate",
        startDate = startDate,
        endDate = endDate,
        semester = semester,
        week = week,
        dailySchedules = dailySchedules
    )
}

fun WeeklyScheduleEntity.toWeeklyScheduleData(): WeeklyScheduleData{
    return WeeklyScheduleData(
        startDate = startDate,
        endDate = endDate,
        semester = semester,
        week = week,
        dailySchedules = dailySchedules
    )
}