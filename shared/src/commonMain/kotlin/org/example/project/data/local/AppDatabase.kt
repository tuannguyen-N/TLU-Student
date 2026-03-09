package org.example.project.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.project.data.local.dao.FeatureDao
import org.example.project.data.local.entity.FeatureEntity

@Database(entities = [FeatureEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun featureDao(): FeatureDao
}