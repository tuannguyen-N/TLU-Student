package org.example.project.data.remote.dto.me

import kotlinx.serialization.Serializable

@Serializable
data class IdentityCard(
    val cardNumber: String,
    val cardType: String,
    val issuedDate: String,
    val issuedPlace: String
)