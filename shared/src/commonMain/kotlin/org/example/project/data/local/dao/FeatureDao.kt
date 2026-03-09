package org.example.project.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.entity.FeatureEntity

@Dao
interface FeatureDao {
    @Query("SELECT * FROM quick_access_features ORDER BY `order`")
    fun getQuickAccessList(): Flow<List<FeatureEntity>>

    @Query("SELECT COUNT(*) FROM quick_access_features")
    suspend fun getQuickAccessCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(features: List<FeatureEntity>)

    @Delete
    suspend fun delete(feature: FeatureEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(feature: FeatureEntity)

    @Query("UPDATE quick_access_features SET `order` = :order WHERE type = :type")
    suspend fun updateOrder(type: String, order: Int)
}