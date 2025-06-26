package com.example.se114_whatthefood_fe.data.remote

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
// auth API
data class LoginRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String
)

data class LoginResponse(
    @SerializedName("accessToken")
    val token: String
)

data class UserInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("pfpUrl")
    val pfpUrl: String?
)

interface ApiService {
    // auth API
    @Headers("Content-Type: application/json")
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>


    @GET("users/info")
    suspend fun getUserInfo(@Header("Authorization") token: String): Response<UserInfo>
}