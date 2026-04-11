package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.data.remote.dto.quote.QuoteResponse

class QuoteApi(
    private val client: HttpClient
) {
    suspend fun getRandomQuote(): QuoteResponse {
        return client
            .get("https://zenquotes.io/api/random")
            .body<List<QuoteResponse>>()
            .first()
    }
}