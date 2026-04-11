package org.example.project.domain.repository

import org.example.project.data.local.dao.QuoteDao
import org.example.project.data.local.entity.QuoteEntity
import org.example.project.data.mapper.toEntity
import org.example.project.data.mapper.today
import org.example.project.data.remote.api.QuoteApi
import org.example.project.data.remote.dto.quote.QuoteResponse

class QuoteRepository(
    private val api: QuoteApi, private val dao: QuoteDao
) {
    suspend fun getDailyQuote(): QuoteEntity {
        val today = today.toString()
        val localQuote = dao.getQuoteByDate(today)

        if (localQuote != null) {
            return localQuote
        } else {
            val remote = getRemoteQuote().toEntity()
            dao.replaceQuote(remote)
            return remote
        }
    }

    suspend fun getRemoteQuote(): QuoteResponse {
        return try {
            api.getRandomQuote()
        } catch (e: Exception) {
            QuoteResponse(
                a = "NELSON MANDELA",
                h = "",
                q = "Giáo dục là vũ khí mạnh nhất mà bạn có thể dùng để thay đổi thế giới."
            )
        }
    }
}