package org.example.project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.entity.NotificationEntity

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY createdAt DESC, id DESC")
    fun observeNotifications(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("DELETE FROM notifications")
    suspend fun clearNotifications()

    @Query("SELECT * FROM notifications WHERE sender = :sender ORDER BY createdAt DESC")
    fun observeNotificationsBySender(sender: String): Flow<List<NotificationEntity>>

    @Query("DELETE FROM notifications WHERE sender = :sender")
    suspend fun clearNotificationsBySender(sender: String)
}