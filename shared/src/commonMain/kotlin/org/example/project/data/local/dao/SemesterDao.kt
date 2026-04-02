package org.example.project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.example.project.data.local.entity.SemesterEntity

@Dao
interface SemesterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSemesters(semesters: List<SemesterEntity>)

    @Query("SELECT * FROM semesters")
    suspend fun getAllSemesters(): List<SemesterEntity>

    @Query("DELETE FROM semesters")
    suspend fun clearSemesters()
}
