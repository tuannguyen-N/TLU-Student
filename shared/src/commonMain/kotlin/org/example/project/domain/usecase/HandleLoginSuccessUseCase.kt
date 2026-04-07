package org.example.project.domain.usecase

import org.example.project.domain.model.AppResult
import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.repository.NotificationRepository

class HandleLoginSuccessUseCase(
    private val authRepository: AuthRepository,
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(
        token: String,
        deviceId: String
    ): AppResult<Unit> {
        return when (val loginResult = authRepository.login(token, deviceId)) {
            is AppResult.Failure -> loginResult

            is AppResult.Success -> {
                notificationRepository.prepareNotification()
                val readIds = notificationRepository.getReadNotifications()
                when (val markResult = notificationRepository.markReadNotification(readIds)) {
                    is AppResult.Failure -> markResult
                    is AppResult.Success -> AppResult.Success(Unit)
                }
            }
        }
    }
}