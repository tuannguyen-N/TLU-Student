package org.example.project.data.remote.dto.quote

import kotlinx.serialization.Serializable

@Serializable
data class QuoteResponse(
    val a: String,
    val h: String,
    val q: String
)