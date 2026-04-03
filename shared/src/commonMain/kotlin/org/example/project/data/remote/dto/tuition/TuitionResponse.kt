package org.example.project.data.remote.dto.tuition

import kotlinx.serialization.Serializable

@Serializable
data class TuitionResponse(
    val code: Int,
    val data: List<TuitionData>?,
    val message: String
)