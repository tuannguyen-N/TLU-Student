package org.example.project.data.remote.dto.news

data class NewsResponse(
    val code: Int,
    val `data`: List<Data>,
    val message: String
)