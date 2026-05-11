package org.example.project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "performed_alerts")
data class PerformedAlertEntity(
    @PrimaryKey
    val studentId: String,
    val notificationId: Int
)
