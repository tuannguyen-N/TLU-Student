package org.example.project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.example.project.data.local.entity.WeeklyScheduleEntity

@Dao
interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaySchedule(schedule: WeeklyScheduleEntity)

    @Query("SELECT * FROM schedule WHERE startDate = :startDate AND endDate = :endDate")
    suspend fun getWeeklySchedule(startDate: String, endDate: String): WeeklyScheduleEntity?
}