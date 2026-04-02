package org.example.project.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import org.example.project.data.local.dao.FeatureDao
import org.example.project.data.local.dao.ScheduleDao
import org.example.project.data.local.entity.FeatureEntity
import org.example.project.data.local.entity.WeeklyScheduleEntity
import org.example.project.data.mapper.ScheduleTypeConverter

import org.example.project.data.local.dao.SemesterDao
import org.example.project.data.local.entity.SemesterEntity

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

@Database(entities = [FeatureEntity::class, WeeklyScheduleEntity::class, SemesterEntity::class], version = 1)
@TypeConverters(ScheduleTypeConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun featureDao(): FeatureDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun semesterDao(): SemesterDao
}