package org.example.project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.entity.PaymentStatusEntity

@Dao
interface PaymentStatusDao {
    @Query("SELECT * FROM payment_status WHERE tuitionId = :tuitionId LIMIT 1")
    fun getByTuitionId(tuitionId: Int): Flow<PaymentStatusEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PaymentStatusEntity)

    @Query("DELETE FROM payment_status WHERE tuitionId = :tuitionId")
    suspend fun deleteByTuitionId(tuitionId: Int)
}