package org.example.project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DailyQuote")
data class QuoteEntity(
    @PrimaryKey
    val date: String,
    val author: String,
    val quote: String
)