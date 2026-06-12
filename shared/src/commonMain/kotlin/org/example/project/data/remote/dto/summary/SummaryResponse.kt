package org.example.project.data.remote.dto.summary

data class SummaryResponse(
    val fileName: String,
    val success: Boolean,
    val summary: String
)