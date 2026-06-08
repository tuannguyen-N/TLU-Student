package org.example.project.data.mapper

import org.example.project.data.local.entity.QuoteEntity
import org.example.project.data.remote.dto.quote.QuoteResponse

fun QuoteResponse.toMarkedEntity(): QuoteEntity {
    return QuoteEntity(
        date = today.toString(),
        author = a,
        quote = q
    )
}