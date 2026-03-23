package org.example.project.domain.usecase

import org.example.project.domain.repository.AuthRepository

class LogoutUseCase (
    private val repository: AuthRepository
){
    suspend fun signOut() {
        repository.signOut()
    }
}