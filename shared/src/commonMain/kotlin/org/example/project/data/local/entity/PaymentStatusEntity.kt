package org.example.project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_status")
data class PaymentStatusEntity(
    @PrimaryKey
    val transactionCode: String,
    val userId: Int,
    val tuitionId: Int,
    val status: String
)