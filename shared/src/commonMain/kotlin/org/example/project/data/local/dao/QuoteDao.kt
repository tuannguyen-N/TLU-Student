package org.example.project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import org.example.project.data.local.entity.QuoteEntity

@Dao
interface QuoteDao {

    @Query("DELETE FROM DailyQuote")
    suspend fun deleteAll()

    @Insert
    suspend fun insertQuote(quote: QuoteEntity)

    @Transaction
    suspend fun replaceQuote(quote: QuoteEntity) {
        deleteAll()
        insertQuote(quote)
    }

    @Query("SELECT * FROM DailyQuote WHERE date = :date LIMIT 1")
    suspend fun getQuoteByDate(date: String): QuoteEntity?
}