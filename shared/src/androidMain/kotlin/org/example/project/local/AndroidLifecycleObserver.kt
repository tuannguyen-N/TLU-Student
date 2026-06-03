package org.example.project.local

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.example.project.domain.repository.UserRepository
import org.example.project.domain.usecase.StudentUseCase

class AndroidLifecycleObserver(
    private val userRepository: UserRepository,
    private val studentUseCase: StudentUseCase
) : DefaultLifecycleObserver {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        scope.launch {
            studentUseCase.studentInfo
                .filterNotNull()
                .collect { student ->
                    Log.d("LIFECYCLE", "Student loaded: ${student.studentCode}")
                }
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        scope.launch {
            val student = studentUseCase.studentInfo
                .filterNotNull()
                .first()

            userRepository.updateOnlineStatus(
                true,
                student.studentCode.lowercase()
            )
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        scope.launch {
            val student = studentUseCase.studentInfo
                .filterNotNull()
                .first()

            userRepository.updateOnlineStatus(
                false,
                student.studentCode.lowercase()
            )
        }
    }
}