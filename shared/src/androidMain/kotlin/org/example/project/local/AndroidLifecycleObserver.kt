package org.example.project.local

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.example.project.domain.repository.PresenceRepository
import org.example.project.domain.repository.UserRepository
import org.example.project.domain.usecase.StudentUseCase

class AndroidLifecycleObserver(
    private val userRepository: UserRepository,
    private val studentUseCase: StudentUseCase,
    private val presenceRepository: PresenceRepository
) : DefaultLifecycleObserver {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        scope.launch {
            val student = studentUseCase.studentInfo
                .filterNotNull()
                .first()

            presenceRepository.setupPresence(
                student.studentCode.lowercase()
            )
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        scope.launch {
            val student = studentUseCase.studentInfo.filterNotNull().first()
            val studentId = student.studentCode.lowercase()

            presenceRepository.goOnline(studentId)

//            userRepository.updateOnlineStatus(
//                true,
//                student.studentCode.lowercase()
//            )
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        scope.launch {
            val student = studentUseCase.studentInfo.filterNotNull().first()
            val studentId = student.studentCode.lowercase()

            presenceRepository.goOffline(studentId)

//            userRepository.updateOnlineStatus(
//                false,
//                student.studentCode.lowercase()
//            )
        }
    }
}