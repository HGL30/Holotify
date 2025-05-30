package com.example.holotify.model

data class ApiResponse(val message: String)

data class LoginResponse(
    val message: String,
    val token: String,
    val user: User
)
