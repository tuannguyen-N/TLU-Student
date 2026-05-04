package org.example.project.presentations.screen.tuition_payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.PaymentRepository
import org.example.project.domain.repository.TuitionRepository

class TuitionPaymentViewModelFactory(
    private val tuitionRepository: TuitionRepository,
    private val paymentRepository: PaymentRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TuitionPaymentViewModel::class.java)) {
            return TuitionPaymentViewModel(tuitionRepository,paymentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}