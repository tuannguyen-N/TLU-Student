package org.example.project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.entity.PerformedAlertEntity

@Dao
interface AlertDao {
    @Insert
    suspend fun insertPerformedAlert(performedAlert: PerformedAlertEntity)

    @Query("SELECT * FROM performed_alerts WHERE studentId = :studentId")
    suspend fun getAllPerformedAlerts(studentId: String): List<PerformedAlertEntity>

    @Query("DELETE FROM PERFORMED_ALERTS WHERE studentId = :studentId AND notificationId =:notificationId")
    suspend fun deletePerformedAlert(notificationId: Int, studentId: String)

    @Query("SELECT * FROM performed_alerts WHERE studentId =:studentId")
    fun observePerformedAlerts(studentId: String): Flow<List<PerformedAlertEntity>>
}