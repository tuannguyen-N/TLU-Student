package org.example.project.domain.repository

import org.example.project.data.mapper.ErrorMapper
import org.example.project.data.remote.api.AttendanceApi
import org.example.project.data.remote.dto.attendance.AttendanceRequest
import org.example.project.domain.model.AppResult

class AttendanceRepository(
    private val api: AttendanceApi
) {
    suspend fun checkIn(qrToken: String, latitude: Double, longitude: Double): AppResult<String> {
        return try {
            val request = AttendanceRequest(qrToken, latitude, longitude)
            val result = api.checkIn(request)
            if (result.code != 0) {
                AppResult.Failure(
                    message = ErrorMapper.mapAttendance(
                        result.code,
                        result.message
                    ).messageVi
                )
            } else {
                AppResult.Success(result.message)
            }
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }
}
