package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.data.remote.dto.tuition.TuitionResponse
import org.example.project.data.remote.dto.tuition_detail.TuitionDetailResponse

class TuitionApi(
    private val client: HttpClient
) {
    suspend fun getTuition(): TuitionResponse {
        return client.get("/api/v1/tuition").body()
    }

    suspend fun getDetailTuition(invoiceId: Int): TuitionDetailResponse {
        return client.get("/api/v1/tuition/${invoiceId}").body()
    }
}