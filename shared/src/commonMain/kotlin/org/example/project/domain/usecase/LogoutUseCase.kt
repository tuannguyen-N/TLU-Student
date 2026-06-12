package org.example.project.domain.usecase

import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.repository.StudentRepository
import org.example.project.domain.repository.UserRepository

class LogoutUseCase(
    private val repository: AuthRepository,
    private val userRepository: UserRepository,
    private val studentRepository: StudentRepository
) {
    suspend fun signOut() {
        val studentCode = studentRepository.studentInfo.value?.studentCode ?: ""
        repository.signOut()
        studentRepository.clearStudentInfo()
        if (studentCode.isNotEmpty()) {
            userRepository.removeToken(studentCode)
        }
    }
}