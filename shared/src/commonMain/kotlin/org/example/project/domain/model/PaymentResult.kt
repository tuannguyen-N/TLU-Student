package org.example.project.domain.model

sealed interface PaymentResult {
    class Success(val txnRef: String?) : PaymentResult
    class Failure(val code: String?) : PaymentResult
}