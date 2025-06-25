package com.example.se114_whatthefood_fe.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(
    val phoneNumber: String,
    val password: String
)

data class LoginResponse(
    val token: String
)

interface ApiService {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}