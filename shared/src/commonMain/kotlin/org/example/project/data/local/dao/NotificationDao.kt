package org.example.project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.entity.NotificationEntity

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReedNotification(notificationId: NotificationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReedNotifications(notifications: List<NotificationEntity>)

    @Query("SELECT * FROM notifications")
    suspend fun getReadNotifications(): List<NotificationEntity>

    @Query("SELECT id FROM notifications")
    fun observeReadNotifications(): Flow<List<Int>>
}