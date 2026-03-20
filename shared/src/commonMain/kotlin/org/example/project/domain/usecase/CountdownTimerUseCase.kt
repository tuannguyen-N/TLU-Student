package org.example.project.domain.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountdownTimerUseCase {
    fun countdown(seconds: Int): Flow<Int> = flow {
        for (i in seconds downTo 0) {
            emit(i)
            if (i > 0) delay(1000L)
        }
    }
}