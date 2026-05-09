package org.example.project.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val code: Int,
    val message: String,
    val data: String?
)