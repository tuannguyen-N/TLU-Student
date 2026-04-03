package org.example.project.data.remote.dto.tuition_detail

import kotlinx.serialization.Serializable

@Serializable
data class TuitionDetailResponse(
    val code: Int,
    val data: TuitionDetailData?,
    val message: String
)