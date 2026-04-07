package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.data.remote.dto.news.NewsResponse
import org.example.project.data.remote.dto.news.Top5NewsResponse

class NewsApi(
    private val client: HttpClient
) {
    suspend fun getTop5News() : Top5NewsResponse{
        return client.get("/api/v1/news/top5").body()
    }

    suspend fun getNews(): NewsResponse {
        return client.get("/api/v1/news").body()
    }
}