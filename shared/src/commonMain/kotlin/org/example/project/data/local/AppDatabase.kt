package org.example.project.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import org.example.project.data.local.dao.AlertDao
import org.example.project.data.local.dao.FeatureDao
import org.example.project.data.local.dao.MarkedNotificationDao
import org.example.project.data.local.dao.MessageDao
import org.example.project.data.local.dao.NotificationDao
import org.example.project.data.local.dao.QuoteDao
import org.example.project.data.local.dao.ScheduleDao
import org.example.project.data.local.dao.SemesterDao
import org.example.project.data.local.entity.FeatureEntity
import org.example.project.data.local.entity.MessageEntity
import org.example.project.data.local.entity.MarkedNotificationEntity
import org.example.project.data.local.entity.NotificationEntity
import org.example.project.data.local.entity.PerformedAlertEntity
import org.example.project.data.local.entity.QuoteEntity
import org.example.project.data.local.entity.SemesterEntity
import org.example.project.data.local.entity.WeeklyScheduleEntity
import org.example.project.data.mapper.ScheduleTypeConverter

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

@Database(
    entities = [FeatureEntity::class, WeeklyScheduleEntity::class, SemesterEntity::class, MarkedNotificationEntity::class, QuoteEntity::class, PerformedAlertEntity::class, NotificationEntity::class, MessageEntity::class],
    version = 5
)
@TypeConverters(ScheduleTypeConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun featureDao(): FeatureDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun notificationDao(): NotificationDao
    abstract fun semesterDao(): SemesterDao
    abstract fun markedNotificationDao(): MarkedNotificationDao
    abstract fun quoteDao(): QuoteDao
    abstract fun alertDao(): AlertDao
    abstract fun messageDao(): MessageDao
}