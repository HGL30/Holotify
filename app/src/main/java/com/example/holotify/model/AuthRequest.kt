// AuthRequests.kt
package com.example.holotify.model // (hoặc package bạn đang dùng)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)