package org.example.project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.entity.MarkedNotificationEntity

@Dao
interface MarkedNotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReadNotification(notificationId: MarkedNotificationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReadNotifications(notifications: List<MarkedNotificationEntity>)

    @Query("SELECT * FROM marked_notifications")
    suspend fun getReadNotifications(): List<MarkedNotificationEntity>

    @Query("SELECT id FROM marked_notifications")
    fun observeReadNotifications(): Flow<List<Int>>
}