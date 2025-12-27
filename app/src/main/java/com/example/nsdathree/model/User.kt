package com.example.nsdathree.model

data class User(
    val userId: String = "",
    val email: String = "",
    val displayName: String? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
