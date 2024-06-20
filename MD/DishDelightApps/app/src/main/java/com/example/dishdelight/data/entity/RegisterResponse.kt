package com.example.dishdelight.data.entity

import com.google.gson.annotations.SerializedName

//register
data class RegisterResponse(
    @field:SerializedName("message")
    val message: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

//login
data class LoginResponse(
    @field:SerializedName("token")
    val token: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

