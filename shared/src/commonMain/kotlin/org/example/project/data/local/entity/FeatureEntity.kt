package org.example.project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.project.domain.model.FeatureType

@Entity(tableName = "quick_access_features")
data class FeatureEntity(
    @PrimaryKey
    val name: String,
    val type: FeatureType,
    val order: Int
)
